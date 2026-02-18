# Longview

![Longview](https://cdn.modrinth.com/data/cached_images/90892d55de73134985fb69f4e644ce4dfb09ba93.png)

![A comparison between having Longview installed and not having it installed. The cold pig is visibly jagged on the "Longview Off" shot and Z-fighting is visible on the waterlogged leaves. The "Longview On" shot shows the exact same scene but without the jaggies and with waterlogged leaves working as intended.](.docs/longview_comparison.webp)

Longview is a mod that fixes glitches such as Z-fighting and jaggies on far distances.

This is accomplished through two techniques:
1) By changing the Z coordinate limits from [-1,1] to [0,1] using the `GL_ARB_CLIP_CONTROL` extension (officially part of OpenGL 4.5)
2) By applying the reversed Z technique, where the far plane and the near plane are swapped in the renderer.

This ensures that the depth buffer will be evenly distributed no matter the distance, fixing all sorts of glitches that happens from a distance!

## Compatibility

This mod requires a device supporting either the `GL_ARB_CLIP_CONTROL` extension or OpenGL 4.5 in order to apply Technique 1.

On devices that don't support either, like ones running macOS, Technique 2 will still be applied, although since the Z coordinate limits will remain as [-1,1], flipping them will do nothing at all considering said limit is effectively symmetric.

## Acknowledgements

- [This Godot Engine blog post](https://godotengine.org/article/introducing-reverse-z/) for introducing me to the reverse Z technique.
- [Depth Precision Visualized](https://developer.nvidia.com/content/depth-precision-visualized): an excellent article covering the topic of depth precision as well as the reverse Z technique.
- The many people who have independently discovered and popularized this technique, including [goddamn Quake developers](https://www.bluesnews.com/abrash/chap66.shtml).
- [qendolin](https://github.com/Qendolin) for putting together the [farz-poc](https://github.com/Qendolin/farz-poc) proof-of-concept and for starting discussions involving Mojang developers.
- [IMS](https://github.com/IMS212) for he's a jolly good fellow, which nobody can deny.