package ioc

import (
	"sync"

	"github.com/IoC-Tools/common"
)

type RootScope struct {
	storage map[string]Dependency
	lock    sync.RWMutex
}

func (s *RootScope) Get(key string) (common.Any, error) {
	var dep Dependency
	s.lock.RLock()
	dep, ok := s.storage[key]
	s.lock.RUnlock()
	if !ok {
		return nil, ErrDependencyNotFound
	}
	return dep, nil
}

func (s *RootScope) Set(key string, value Dependency) error {
	return ErrRootScopeWrite
}
