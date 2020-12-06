package rooms.dto

class CollectionDto(

        var rooms: MutableList<TicketDto> = mutableListOf(),

        var prices: MutableMap<Rooms, Int> = mutableMapOf(),

        var millValues: MutableMap<Rooms, Int> = mutableMapOf(),

        var roomsProbabilities: MutableMap<Rooms, Double> = mutableMapOf()

)
