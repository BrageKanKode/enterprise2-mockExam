package usercollections.dto

import io.swagger.annotations.ApiModelProperty

enum class Command {
//    OPEN_PACK,
    MILL_TICKET,
    BUY_TICKET
}

data class PatchUserDto(

        @get:ApiModelProperty("Command to execute on a user's collection")
        var command: Command? = null,

        @get:ApiModelProperty("Optional card id, if the command requires one")
        var ticketId: String? = null
)
