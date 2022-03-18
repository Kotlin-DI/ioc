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
		return nil, ScopeErrorNotFound{key: key}
	}
	return dep, nil
}

func (s *RootScope) Set(key string, value common.Dependency) error {
	return ScopeErrorImmutable{}
}

func (s *RootScope) Remove(key string) {}

func NewRootScope() *RootScope {
	var root = new(RootScope)
	root.storage = make(map[string]common.Dependency)

	root.storage["IoC.REGISTER"] = func(a []common.Any) (common.Any, error) {
		var key = a[0].(string)
		var value = a[1].(common.Dependency)
		var scope = GetCurrentScope()

		return &RegisterCommand{key: key, value: value, scope: scope}, nil
	}

	root.storage["IoC.UNREGISTER"] = func(a []common.Any) (common.Any, error) {
		var key = a[0].(string)
		var scope = GetCurrentScope()

		return &UnregisterCommand{key: key, scope: scope}, nil
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
		executionScope := a[0].(IScope)
		fn := a[1].(func() error)
		return &ScopeExecutionCommand{executionScope: executionScope, fn: fn}, nil
	}

	root.storage["IoC.EXECUTE_IN_NEW_SCOPE"] = func(a []common.Any) (common.Any, error) {
		fn := a[0].(func() error)
		current := GetCurrentScope()
		newScope, _ := current.Get("Scope.NEW")
		executionScope, _ := newScope([]common.Any{current})
		return &ScopeExecutionCommand{executionScope: executionScope.(IScope), fn: fn}, nil
	}

	return root
}
