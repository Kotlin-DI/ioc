package ioc

import "errors"

var ErrDependencyNotFound = errors.New("dependecy not found")
var ErrRootScopeWrite = errors.New("attempt to write in RootScope")
