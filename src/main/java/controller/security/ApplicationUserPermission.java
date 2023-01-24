package controller.security;

public enum ApplicationUserPermission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    EQUIPMENT_ITEM_READ("equipmentItem:read"),
    EQUIPMENT_ITEM_WRITE("equipmentItem:write"),
    TOKEN_READ("token:read"),
    TOKEN_WRITE("token:read");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
