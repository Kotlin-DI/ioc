package ioc

import (
	"github.com/IoC-Tools/common"
	"github.com/IoC-Tools/ioc/scopes"
)

func init() {
	scopes.InitScope()
}

func Resolve[T common.Any](key string, args ...common.Any) (T, error) {
	var (
		result     T
		ok         bool
		dependency common.Dependency
		err        error
	)
	var scope = scopes.GetCurrentScope()
	dependency, err = scope.Get(key)
	if err != nil {
		return result, ResolveError{key: key, cause: err.Error()}
	} else {
		_result, err := dependency(args)
		if err != nil {
			return result, ResolveError{key: key, cause: err.Error()}
		}
		result, ok = _result.(T)
		if !ok {
			return result, ResolveError{key: key, cause: "Unable to cast result"}
		}
		return result, nil
	}
}
