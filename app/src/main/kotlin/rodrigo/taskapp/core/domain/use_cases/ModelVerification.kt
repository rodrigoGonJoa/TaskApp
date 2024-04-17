package rodrigo.taskapp.core.domain.use_cases

import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result

abstract class ModelVerification<MODEL: BaseModel<*>, MODEL_ERROR: Error> {

    abstract val verifications: List<(MODEL) -> Result<Unit, MODEL_ERROR>>

    open operator fun invoke(model: MODEL): Result<Unit, MODEL_ERROR> {
        return verifications.find {verification ->
            verification(model) is Result.Error
        }?.invoke(model) ?: Result.Success(Unit)
    }
}
