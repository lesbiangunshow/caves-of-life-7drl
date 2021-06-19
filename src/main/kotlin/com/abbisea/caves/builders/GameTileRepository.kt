package com.abbisea.caves.builders

import com.abbisea.caves.builders.GameColors.ACCENT_COLOR
import com.abbisea.caves.builders.GameColors.BAT_COLOR
import com.abbisea.caves.builders.GameColors.BAT_MEAT_COLOR
import com.abbisea.caves.builders.GameColors.FLOOR_BACKGROUND
import com.abbisea.caves.builders.GameColors.FLOOR_FOREGROUND
import com.abbisea.caves.builders.GameColors.FUNGUS_COLOR
import com.abbisea.caves.builders.GameColors.UNREVEALED_COLOR
import com.abbisea.caves.builders.GameColors.WALL_BACKGROUND
import com.abbisea.caves.builders.GameColors.WALL_FOREGROUND
import com.abbisea.caves.builders.GameColors.ZIRCON_COLOR
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {

    val EMPTY: CharacterTile = Tile.empty()

    val UNREVEALED = Tile.newBuilder()
        .withCharacter(' ')
        .withBackgroundColor(UNREVEALED_COLOR)
        .buildCharacterTile()

    val FLOOR: CharacterTile = Tile.newBuilder()
        .withCharacter(Symbols.INTERPUNCT)
        .withForegroundColor(FLOOR_FOREGROUND)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val WALL: CharacterTile = Tile.newBuilder()
        .withCharacter('#')
        .withForegroundColor(WALL_FOREGROUND)
        .withBackgroundColor(WALL_BACKGROUND)
        .buildCharacterTile()

    val PLAYER = Tile.newBuilder()
        .withCharacter('@')
        .withForegroundColor(ACCENT_COLOR)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val STAIRS_UP = Tile.newBuilder()
        .withCharacter('<')
        .withForegroundColor(ACCENT_COLOR)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val STAIRS_DOWN = Tile.newBuilder()
        .withCharacter('>')
        .withForegroundColor(ACCENT_COLOR)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val FUNGUS = Tile.newBuilder()
        .withCharacter('f')
        .withForegroundColor(FUNGUS_COLOR)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val BAT = Tile.newBuilder()
        .withCharacter('b')
        .withForegroundColor(BAT_COLOR)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val BAT_MEAT = Tile.newBuilder()
        .withCharacter('m')
        .withForegroundColor(BAT_MEAT_COLOR)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val ZIRCON = Tile.newBuilder()
        .withCharacter(',')
        .withForegroundColor(ZIRCON_COLOR)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val CLUB = Tile.newBuilder()
        .withCharacter('(')
        .withForegroundColor(ANSITileColor.GRAY)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val DAGGER = Tile.newBuilder()
        .withCharacter('(')
        .withForegroundColor(ANSITileColor.WHITE)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val SWORD = Tile.newBuilder()
        .withCharacter('(')
        .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val STAFF = Tile.newBuilder()
        .withCharacter('(')
        .withForegroundColor(ANSITileColor.YELLOW)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val JACKET = Tile.newBuilder()
        .withCharacter('[')
        .withForegroundColor(ANSITileColor.GRAY)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val LIGHT_ARMOR = Tile.newBuilder()
        .withCharacter('[')
        .withForegroundColor(ANSITileColor.GREEN)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val MEDIUM_ARMOR = Tile.newBuilder()
        .withCharacter('[')
        .withForegroundColor(ANSITileColor.WHITE)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()

    val HEAVY_ARMOR = Tile.newBuilder()
        .withCharacter('[')
        .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
        .withBackgroundColor(FLOOR_BACKGROUND)
        .buildCharacterTile()
}