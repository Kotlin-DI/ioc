package scopes

import (
	"github.com/IoC-Tools/common"
	"github.com/IoC-Tools/common/uobject"
)

type IScope = uobject.UObject[common.Dependency]
