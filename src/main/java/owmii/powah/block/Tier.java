package owmii.powah.block;

import owmii.lib.registry.IVariant;

public enum Tier implements IVariant<Tier> {
    STARTER(0xA7A7A7),
    BASIC(0xA3AB9F),
    HARDENED(0xBBA993),
    BLAZING(0xE4B040),
    NIOTIC(0x13EED2),
    SPIRITED(0xAFE241),
    NITRO(0xD7746C),   
    OVERCHARGED(0xE500DC),
    CREATIVE(0x8D29AD);

    private final int color;

    Tier(int color) {
        this.color = color;
    }

    @Override
    public Tier[] getVariants() {
        return values();
    }

    public static Tier[] getVariantsWithoutStarter() {
        return new Tier[]{BASIC, HARDENED, BLAZING, NIOTIC, SPIRITED, NITRO, OVERCHARGED, CREATIVE};
    }
    public static Tier[] getNormalVariants() {
        return new Tier[]{STARTER, BASIC, HARDENED, BLAZING, NIOTIC, SPIRITED, NITRO, OVERCHARGED};
    }

    public int getColor() {
        return color;
    }
}
