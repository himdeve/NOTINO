package sk.himdeve.autoinitializedcompiler

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import com.squareup.anvil.compiler.internal.testing.compileAnvil
import com.tschuchort.compiletesting.KotlinCompilation
import org.junit.Test

/**
 * Created by Robin Himdeve on 9/7/2021.
 */
@ExperimentalAnvilApi
class AutoInitializedCodeGeneratorTest {
    @Test
    fun `should properly run auto initialized compiler and generate file`() {
        compile(
            """
        package sk.o2.autoinitializedcompiler

        import com.squareup.anvil.annotations.ContributesTo
        import dagger.Module
        import dagger.Provides
        import dagger.multibindings.IntoSet
        import kotlin.Suppress
        import kotlin.jvm.JvmStatic
        import sk.o2.base.di.AppScope
        import sk.o2.base.di.AutoInitializedSet
        import sk.o2.base.Scoped
        
        @Module
        @ContributesTo(AppScope::class)
        object FooManagerScopedModule {
            @Provides
            @JvmStatic
            @AppScope
            @AutoInitializedSet(AppScope::class)
            fun fooScoped(foo: Foo): Source = Source(foo)
        }
            """
        ) {
            assertThat(exitCode).isEqualTo(KotlinCompilation.ExitCode.OK)
        }
    }

    @Suppress("CHANGING_ARGUMENTS_EXECUTION_ORDER_FOR_NAMED_VARARGS")
    private fun compile(
        vararg sources: String,
        block: KotlinCompilation.Result.() -> Unit = { }
    ): KotlinCompilation.Result = compileAnvil(
        sources = sources,
        block = block
    )
}