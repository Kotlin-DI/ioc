package scopes

import (
	"sync"

	"github.com/IoC-Tools/common"
)

type RootScope struct {
	storage map[string]common.Dependency
	lock    sync.RWMutex
}

func (s *RootScope) Get(key string) (common.Dependency, error) {
	var dep common.Dependency
	s.lock.RLock()
	dep, ok := s.storage[key]
	s.lock.RUnlock()
	if !ok {
		return nil, ScopeErrorNotFound{}
	}
	return dep, nil
}

func (s *RootScope) Set(key string, value common.Dependency) error {
	return ScopeErrorImmutable{}
}

func NewRootScope() *RootScope {
	var root = new(RootScope)
	root.storage = make(map[string]common.Dependency)

	root.storage["IoC.REGISTER"] = func(a []common.Any) (common.Any, error) {
		var key = a[0].(string)
		var value = a[1].(common.Dependency)
		var scope = GetCurrentScope()
		return RegisterCommand{scope: scope, key: key, value: value}, nil
	}

	root.storage["Scope.NEW"] = func(a []common.Any) (common.Any, error) {
		var parent IScope
		if len(a) == 1 {
			parent = a[0].(IScope)
		} else {
			parent = GetCurrentScope()
		}
		return NewScope(parent), nil
	}

	root.storage["Scope.CURRENT"] = func(a []common.Any) (common.Any, error) {
		return GetCurrentScope(), nil
	}

	root.storage["IoC.EXECUTE_IN_SCOPE"] = func(a []common.Any) (common.Any, error) {
		var executionScope = a[0].(IScope)
		var fn = a[1].(func() error)
		return ScopeExecutionCommand{executionScope: executionScope, fn: fn}, nil

	}

	return root
}
