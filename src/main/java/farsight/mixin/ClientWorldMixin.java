package farsight.mixin;

import farsight.FarsightClientChunkManager;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientLevel.class)
/**
 * Exchanges the client's chunk map with a custom implementation, which can handle chunks at any distance apart fine
 */
public class ClientWorldMixin
{
    @Shadow
    @Final
    @Mutable
    private ClientChunkCache chunkSource;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(
            ClientPacketListener clientPacketListener,
            ClientLevel.ClientLevelData clientLevelData,
            ResourceKey resourceKey,
            Holder holder, int i, int j,
            LevelRenderer levelRenderer,
            boolean bl, long l, int k, CallbackInfo ci)
    {
        chunkSource = new FarsightClientChunkManager((ClientLevel) ((Object) this));
    }
}
