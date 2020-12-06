package rooms

import rooms.dto.CollectionDto
import rooms.dto.Rooms.*
import rooms.dto.TicketDto


object RoomCollection {

    fun get() : CollectionDto {
        val dto = CollectionDto()

        dto.prices.run {
            put(BRONZE, 100)
            put(SILVER, 500)
            put(GOLD, 1_000)
            put(PINK_DIAMOND, 100_000)
        }
        dto.prices.forEach { dto.millValues[it.key] = it.value / 4 }

        dto.roomsProbabilities.run {
            put(SILVER, 0.10)
            put(GOLD, 0.01)
            put(PINK_DIAMOND, 0.001)
            put(BRONZE, 1 - get(SILVER)!! - get(GOLD)!! - get(PINK_DIAMOND)!!)
        }

        addTickets(dto)

        return dto
    }

    private fun addTickets(dto: CollectionDto) {

        dto.rooms.run {
            add(TicketDto("c000", "Green Mold", "lore ipsum", BRONZE, "035-monster.svg"))
            add(TicketDto("c001", "Opera Singer", "lore ipsum", BRONZE, "056-monster.svg"))
            add(TicketDto("c002", "Not Stitch", "lore ipsum", BRONZE, "070-monster.svg"))
            add(TicketDto("c003", "Assault Hamster", "lore ipsum", BRONZE, "100-monster.svg"))
            add(TicketDto("c004", "WTF?!?", "lore ipsum", BRONZE, "075-monster.svg"))
            add(TicketDto("c005", "Stupid Lump", "lore ipsum", BRONZE, "055-monster.svg"))
            add(TicketDto("c006", "Sad Farter", "lore ipsum", BRONZE, "063-monster.svg"))
            add(TicketDto("c007", "Smelly Tainter", "lore ipsum", BRONZE, "050-monster.svg"))
            add(TicketDto("c008", "Hårboll", "lore ipsum", BRONZE, "019-monster.svg"))
            add(TicketDto("c009", "Blue Horned", "lore ipsum", BRONZE, "006-monster.svg"))
            add(TicketDto("c010", "Børje McTrumf", "lore ipsum", SILVER, "081-monster.svg"))
            add(TicketDto("c011", "Exa Nopass", "lore ipsum", SILVER, "057-monster.svg"))
            add(TicketDto("c012", "Dick Tracy", "lore ipsum", SILVER, "028-monster.svg"))
            add(TicketDto("c013", "Marius Mario", "lore ipsum", SILVER, "032-monster.svg"))
            add(TicketDto("c014", "Patrick Stew", "lore ipsum", SILVER, "002-monster.svg"))
            add(TicketDto("c015", "Fluffy The Hugger of Death", "lore ipsum", GOLD, "036-monster.svg"))
            add(TicketDto("c016", "Gary The Wise", "lore ipsum", GOLD, "064-monster.svg"))
            add(TicketDto("c017", "Grump-Grump The Grumpy One", "lore ipsum", GOLD, "044-monster.svg"))
            add(TicketDto("c018", "Bert-ho-met The Polite Guy", "lore ipsum", GOLD, "041-monster.svg"))
            add(TicketDto("c019", "Bengt The Destroyer", "lore ipsum", PINK_DIAMOND, "051-monster.svg"))
        }

        assert(dto.rooms.size == dto.rooms.map { it.ticketId }.toSet().size)
        assert(dto.rooms.size == dto.rooms.map { it.name }.toSet().size)
        assert(dto.rooms.size == dto.rooms.map { it.imageId }.toSet().size)
    }
}
