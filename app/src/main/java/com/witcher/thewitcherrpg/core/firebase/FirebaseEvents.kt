package com.witcher.thewitcherrpg.core.firebase

enum class FirebaseEvents(event: String) {
    NEW_CHARACTER_CREATED("new_character_created"),
    NEW_CHARACTER_CREATE_ERROR("new_character_create_error"),
    NEW_CHARACTER_ADDED("new_character_added"),
    CHARACTER_ADD_ERROR("character_add_error"),
    CHARACTER_SAVED("character_saved"),
    CHARACTER_SAVE_ERROR("character_save_error"),
    CHARACTER_DELETED("character_deleted"),
    CHARACTER_DELETE_ERROR("character_delete_error"),
    CUSTOM_WEAPON_ADDED("custom_weapon_added"),
    CUSTOM_WEAPON_ADD_ERROR("custom_weapon_add_error"),
    CUSTOM_WEAPON_INVALID("custom_weapon_invalid_error"),
    CUSTOM_WEAPON_DELETE_ERROR("custom_weapon_delete_error"),
    CUSTOM_EQUIPMENT_ADDED("custom_equipment_added"),
    CUSTOM_EQUIPMENT_ADD_ERROR("custom_equipment_add_error"),
    CUSTOM_EQUIPMENT_INVALID("custom_equipment_invalid_error"),
    CUSTOM_EQUIPMENT_DELETE_ERROR("custom_equipment_delete_error"),
    CUSTOM_MAGIC_ADDED("custom_magic_added"),
    CUSTOM_MAGIC_ADD_ERROR("custom_magic_add_error"),
    CUSTOM_MAGIC_DELETE_ERROR("custom_magic_delete_error"),
}