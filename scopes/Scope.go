package scopes

import (
	"sync"

	"github.com/IoC-Tools/common"
)

type Scope struct {
	parent  IScope
	storage map[string]common.Dependency
	lock    sync.RWMutex
}

func (s *Scope) Get(key string) (common.Dependency, error) {

	var dep common.Dependency
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
			dep = res.(common.Dependency)
		}
	}
	return dep, nil
}

func (s *Scope) Set(key string, value common.Dependency) error {
	s.lock.Lock()
	s.storage[key] = value
	s.lock.Unlock()
	return nil
}

func NewScope(parrent IScope) *Scope {
	var scope = new(Scope)
	scope.parent = parrent
	scope.storage = map[string]common.Dependency{}
	return scope
}
