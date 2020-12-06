package usercollections.model

import rooms.dto.CollectionDto
import rooms.dto.Rooms
import kotlin.math.abs

data class Collection(

        val tickets : List<Ticket>,

        val prices: Map<Rooms, Int>,

//        val millValues: Map<Rarity, Int>,

        val roomsProbabilities: Map<Rooms, Double>
){

    constructor(dto: CollectionDto) : this(
            dto.rooms.map { Ticket(it) },
            dto.prices.toMap(),
//            dto.millValues.toMap(),
            dto.roomsProbabilities.toMap()
    )

    val cardsByRooms : Map<Rooms, List<Ticket>> = tickets.groupBy { it.rooms }

    init{
        if(tickets.isEmpty()){
            throw IllegalArgumentException("No cards")
        }
        Rooms.values().forEach {
            requireNotNull(prices[it])
//            requireNotNull(millValues[it])
            requireNotNull(roomsProbabilities[it])
        }

        val p = roomsProbabilities.values.sum()
        if(abs(1 - p) > 0.00001){
            throw IllegalArgumentException("Invalid probability sum: $p")
        }
    }
}
