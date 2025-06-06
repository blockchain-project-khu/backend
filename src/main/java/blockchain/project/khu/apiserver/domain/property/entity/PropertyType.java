package blockchain.project.khu.apiserver.domain.property.entity;

public enum PropertyType {
    OFFICETEL("오피스텔"),
    APARTMENT("아파트"),
    COMMERCIAL("상가"),
    OFFICE("오피스"),
    ONE_ROOM("원룸"),
    PENSION("펜션");

    private final String displayName;

    PropertyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PropertyType fromDisplayName(String name) {
        for (PropertyType type : values()) {
            if (type.displayName.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown PropertyType display name: " + name);
    }
}
