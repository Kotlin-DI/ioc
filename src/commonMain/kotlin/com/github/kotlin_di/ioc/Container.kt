package com.github.kotlin_di.ioc

import com.github.kotlin_di.ioc.scope.IScope

expect object Container {
    var currentScope: IScope
}
