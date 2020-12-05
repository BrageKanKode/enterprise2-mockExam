package usercollections.dto

import io.swagger.annotations.ApiModelProperty

class PatchResultDto (
        @get:ApiModelProperty("If a card pack was opened, specify which cards were in it")
        var ticketIdsInOpenedPack: MutableList<String> = mutableListOf()
)
