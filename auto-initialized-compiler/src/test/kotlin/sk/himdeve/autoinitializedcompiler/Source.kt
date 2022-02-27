package sk.himdeve.autoinitializedcompiler

import sk.himdeve.base.Scoped

/**
 * Created by Robin Himdeve on 9/7/2021.
 */
class Source(foo: Foo) : Scoped {
    override fun init() {}
    override fun clear() {}
}

class Foo