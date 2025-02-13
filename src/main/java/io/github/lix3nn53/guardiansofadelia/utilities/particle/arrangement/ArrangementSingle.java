package io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.math.RotationHelper;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

import java.util.Random;

public class ArrangementSingle implements ParticleArrangement {
    protected final Particle particle;
    protected final Particle.DustOptions dustOptions;
    protected final double singleMinHeight;
    protected final double singleMaxHeight;
    protected final double singleGap;

    public ArrangementSingle(Particle particle, Particle.DustOptions dustOptions, double singleMinHeight, double singleMaxHeight, double singleGap) {
        this.particle = particle;
        this.dustOptions = dustOptions;
        this.singleMinHeight = singleMinHeight;
        this.singleMaxHeight = singleMaxHeight;
        this.singleGap = singleGap;
    }

    public ArrangementSingle(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("particleType")) {
            configLoadError("particleType");
        }

        this.particle = Particle.valueOf(configurationSection.getString("particleType"));

        if (configurationSection.contains("dustColor")) {
            if (!this.particle.getDataType().equals(Particle.DustOptions.class)) {
                configLoadError("WRONG DUST OPTIONS");
            }

            int dustColor = configurationSection.getInt("dustColor");
            int dustSize = configurationSection.getInt("dustSize");

            dustOptions = new Particle.DustOptions(Color.fromRGB(dustColor), dustSize);
        } else {
            dustOptions = null;
        }

        this.singleMinHeight = configurationSection.contains("singleMinHeight") ? configurationSection.getDouble("singleMinHeight") : 0;
        this.singleMaxHeight = configurationSection.contains("singleMaxHeight") ? configurationSection.getDouble("singleMaxHeight") : 0;
        this.singleGap = configurationSection.contains("singleGap") ? configurationSection.getDouble("singleGap") : 0;
    }

    @Override
    public void play(Location location, Vector offset) {
        Location location1 = location.clone();
        if (offset != null) {
            location1.add(offset);
        }

        if (singleMaxHeight > 0) {
            Random r = new Random();
            double height = singleMinHeight + (singleMaxHeight - singleMinHeight) * r.nextDouble();

            ParticleShapes.playSingleParticleWithHeight(location1, particle, dustOptions, height, singleGap);
        } else {
            ParticleShapes.playSingleParticle(location1, particle, dustOptions);
        }
    }

    @Override
    public void play(Location location, Vector offset, float yaw, float pitch) {
        Vector vector = location.clone().add(offset).toVector();

        RotationHelper.rotateYawPitch(vector, yaw, pitch);

        if (singleMaxHeight > 0) {
            Random r = new Random();
            double height = singleMinHeight + (singleMaxHeight - singleMinHeight) * r.nextDouble();

            ParticleShapes.playSingleParticleWithHeight(location.getWorld(), vector, particle, dustOptions, height, singleGap);
        } else {
            ParticleShapes.playSingleParticle(location.getWorld(), vector, particle, dustOptions);
        }
    }

    public void configLoadError(String section) {
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "ERROR WHILE LOADING PARTICLE: ");
        GuardiansOfAdelia.getInstance().getLogger().info(ChatPalette.RED + "Section: " + section);
    }
}
