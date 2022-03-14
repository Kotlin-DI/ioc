package scopes

import (
	"github.com/davecheney/badidea"
)

type scopes struct {
	root    *RootScope
	current IScope
	scopes  map[int64]IScope
}

var Scopes *scopes = nil

func GetCurrentScope() IScope {
	var guid = badidea.GoroutineID()
	if Scopes == nil {
		Scopes = new(scopes)
		Scopes.scopes = make(map[int64]IScope)
		Scopes.root = NewRootScope()
		Scopes.current = NewScope(Scopes.root)
	}

	scope, ok := Scopes.scopes[guid]
	if !ok {
		Scopes.scopes[guid] = Scopes.current
		scope = Scopes.current
	}
	return scope
}

func SetCurrentScope(scope IScope) error {
	var guid = badidea.GoroutineID()
	if Scopes == nil {
		return ScopeErrorUnset{}
	}
	Scopes.scopes[guid] = scope
	return nil
}
