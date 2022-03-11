package ioc

import (
	"github.com/IoC-Tools/common"
)

type Dependency func([]common.Any) (common.Any, error)
