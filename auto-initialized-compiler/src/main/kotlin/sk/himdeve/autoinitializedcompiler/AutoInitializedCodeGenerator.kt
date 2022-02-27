package sk.himdeve.autoinitializedcompiler

import com.google.auto.service.AutoService
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.api.AnvilCompilationException
import com.squareup.anvil.compiler.api.AnvilContext
import com.squareup.anvil.compiler.api.CodeGenerator
import com.squareup.anvil.compiler.api.GeneratedFile
import com.squareup.anvil.compiler.api.createGeneratedFile
import com.squareup.anvil.compiler.internal.asClassName
import com.squareup.anvil.compiler.internal.buildFile
import com.squareup.anvil.compiler.internal.capitalize
import com.squareup.anvil.compiler.internal.classesAndInnerClass
import com.squareup.anvil.compiler.internal.decapitalize
import com.squareup.anvil.compiler.internal.fqName
import com.squareup.anvil.compiler.internal.functions
import com.squareup.anvil.compiler.internal.hasAnnotation
import com.squareup.anvil.compiler.internal.requireFqName
import com.squareup.anvil.compiler.internal.requireTypeName
import com.squareup.anvil.compiler.internal.safePackageString
import com.squareup.anvil.compiler.internal.scope
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import dagger.Provides
import dagger.multibindings.IntoSet
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.psi.KtFile
import sk.himdeve.base.Scoped
import sk.himdeve.base.di.AutoInitialized
import sk.himdeve.base.di.AutoInitializedSet
import java.io.File

private val daggerModuleFqName = dagger.Module::class.fqName
private val daggerProvidesFqName = Provides::class.fqName
private val autoInitializedFqName = AutoInitialized::class.fqName
private val contributesToFqName = ContributesTo::class.fqName

@ExperimentalAnvilApi
@AutoService(CodeGenerator::class)
class AutoInitializedCodeGenerator : CodeGenerator {
    override fun generateCode(
        codeGenDir: File,
        module: ModuleDescriptor,
        projectFiles: Collection<KtFile>
    ): Collection<GeneratedFile> {
        return projectFiles
            .classesAndInnerClass(module)
            .filter { it.hasAnnotation(daggerModuleFqName, module) }
            .filter { it.hasAnnotation(contributesToFqName, module) }
            .flatMap { clazz ->
                val scope = clazz.scope(contributesToFqName, module)
                val packageName = clazz.containingKtFile.packageFqName.safePackageString(dotSuffix = false)

                clazz.functions(includeCompanionObjects = true)
                    .asSequence()
                    .filter { it.hasAnnotation(daggerProvidesFqName, module) }
                    .filter { it.hasAnnotation(autoInitializedFqName, module) }
                    .map {
                        val typeReference = it.typeReference
                            ?: throw AnvilCompilationException("No type reference", element = it)

                        if (!it.hasAnnotation(scope, module)) {
                            throw AnvilCompilationException(
                                "@AutoInitialized type should be singleton within its scope",
                                element = it
                            )
                        }

                        val type = typeReference.requireFqName(module).shortName().asString()
                        val typeName = typeReference.requireTypeName(module)
                        val moduleName = "${type.capitalize()}ScopedModule"
                        val scopeName = scope.asClassName(module)

                        val contents = generateFileContent(type, typeName, moduleName, packageName, scopeName)

                        createGeneratedFile(
                            codeGenDir = codeGenDir,
                            packageName = packageName,
                            fileName = moduleName,
                            content = contents
                        )
                    }
            }
            .toList()
    }

    override fun isApplicable(context: AnvilContext): Boolean = true

    private fun generateFileContent(
        type: String,
        typeName: TypeName,
        moduleName: String,
        packageName: String,
        scopeName: TypeName,
    ): String {
        val typeDecapitalized = type.decapitalize()

        return FileSpec.buildFile(packageName, moduleName) {
            addType(
                TypeSpec.objectBuilder(moduleName)
                    .addAnnotation(dagger.Module::class)
                    .addAnnotation(
                        AnnotationSpec.builder(ContributesTo::class)
                            .addMember("%T::class", scopeName)
                            .build()
                    )

                    .addFunction(
                        FunSpec
                            .builder("${typeDecapitalized}Scoped")
                            .addAnnotation(
                                AnnotationSpec.builder(AutoInitializedSet::class)
                                    .addMember("%T::class", scopeName)
                                    .build()
                            )
                            .addAnnotation(IntoSet::class)
                            .addAnnotation(JvmStatic::class)
                            .addAnnotation(Provides::class)
                            .addParameter(typeDecapitalized, typeName)
                            .returns(Scoped::class)
                            .addStatement("return $typeDecapitalized")
                            .build()
                    )
                    .build()
            ).build()
        }
    }
}