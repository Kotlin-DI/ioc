package ioc

import (
	"sync"

	"github.com/IoC-Tools/common"
)

type Scope struct {
	parent  IScope
	storage map[string]Dependency
	lock    sync.RWMutex
}

func (s *Scope) Get(key string) (common.Any, error) {
	var dep Dependency
	var err error
	s.lock.RLock()
	dep, ok := s.storage[key]
	s.lock.RUnlock()
	if !ok {
		var res common.Any
		res, err = s.parent.Get(key)
		if err != nil {
			return nil, err
		} else {
			dep = res.(Dependency)
		}
	}
	return dep, nil
}

func (s *Scope) Set(key string, value Dependency) error {
	s.lock.Lock()
	s.storage[key] = value
	s.lock.Unlock()
	return nil
}
