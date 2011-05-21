package com.gemserk.commons.gdx.resources;

import org.w3c.dom.Document;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.gemserk.animation4j.FrameAnimationImpl;
import com.gemserk.animation4j.gdx.Animation;
import com.gemserk.commons.gdx.resources.dataloaders.DisposableDataLoader;
import com.gemserk.commons.gdx.resources.dataloaders.SoundDataLoader;
import com.gemserk.commons.gdx.resources.dataloaders.TextureDataLoader;
import com.gemserk.commons.svg.inkscape.DocumentParser;
import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.dataloaders.DataLoader;
import com.gemserk.resources.resourceloaders.CachedResourceLoader;
import com.gemserk.resources.resourceloaders.ResourceLoader;
import com.gemserk.resources.resourceloaders.ResourceLoaderImpl;

public class LibgdxResourceBuilder {
	
	// TODO: Define folders for each type of resource?

	ResourceManager<String> resourceManager;

	private boolean cacheWhenLoad = false;

	public void setCacheWhenLoad(boolean cacheWhenLoad) {
		this.cacheWhenLoad = cacheWhenLoad;
	}

	public LibgdxResourceBuilder(ResourceManager<String> resourceManager) {
		this.resourceManager = resourceManager;
	}

	public FileHandle internal(String file) {
		return Gdx.files.internal(file);
	}

	public FileHandle absolute(String file) {
		return Gdx.files.absolute(file);
	}

	public void texture(String id, String file) {
		texture(id, internal(file), true);
	}

	public void texture(String id, String file, boolean linearFilter) {
		texture(id, internal(file), linearFilter);
	}

	public void texture(String id, FileHandle fileHandle, boolean linearFilter) {
		resourceManager.add(id, new CachedResourceLoader<Texture>(new ResourceLoaderImpl<Texture>(new TextureDataLoader(fileHandle, linearFilter))));
		if (cacheWhenLoad)
			resourceManager.get(id).get();
	}

	/**
	 * Registers a texture atlas in resources manager.
	 * 
	 * @param id
	 *            The id of the resource.
	 * @param file
	 *            The libgdx internal file path to create the texture atlas.
	 */
	public void textureAtlas(String id, String file) {
		resourceManager.add(id, new CachedResourceLoader<TextureAtlas>(new ResourceLoaderImpl<TextureAtlas>(new DisposableDataLoader<TextureAtlas>(Gdx.files.internal(file)) {
			@Override
			public TextureAtlas load() {
				return new TextureAtlas(fileHandle);
			}
		})));
	}

	/**
	 * registers a new sprite resource builder returning a new sprite each time it is called.
	 */
	public void sprite(String id, final String textureId) {
		resourceManager.add(id, new ResourceLoaderImpl<Sprite>(new DataLoader<Sprite>() {
			@Override
			public Sprite load() {
				Resource<Texture> texture = resourceManager.get(textureId);
				return new Sprite(texture.get());
			}
		}));
	}

	/**
	 * registers a new sprite resource builder returning a new sprite each time it is called.
	 */
	public void sprite(String id, final String textureId, final int x, final int y, final int width, final int height) {
		resourceManager.add(id, new ResourceLoaderImpl<Sprite>(new DataLoader<Sprite>() {
			@Override
			public Sprite load() {
				Resource<Texture> texture = resourceManager.get(textureId);
				return new Sprite(texture.get(), x, y, width, height);
			}
		}));
	}

	/**
	 * Registers with id a new Sprite based on a TextureAtlas region with the same name.
	 * 
	 * @param id
	 *            The identifier of the Resource to register, also the texture region name inside the TextureAtlas.
	 * @param textureAtlasId
	 *            the TextureAtlas resource identifier.
	 */
	public void spriteAtlas(final String id, final String textureAtlasId) {
		resourceManager.add(id, new ResourceLoaderImpl<Sprite>(new DataLoader<Sprite>() {
			@Override
			public Sprite load() {
				TextureAtlas textureAtlas = resourceManager.getResourceValue(textureAtlasId);
				return new Sprite(textureAtlas.findRegion(id));
			}
		}));
	}

	public void animation(String id, final String spriteSheetId, final int x, final int y, final int w, final int h, final int framesCount, //
			final boolean loop, final int time, final int... times) {
		resourceManager.add(id, new ResourceLoaderImpl<Animation>(new DataLoader<Animation>() {

			@Override
			public Animation load() {
				Texture spriteSheet = resourceManager.getResourceValue(spriteSheetId);
				Sprite[] frames = new Sprite[framesCount];
				for (int i = 0; i < frames.length; i++) {
					frames[i] = new Sprite(spriteSheet, x + i * w, y, w, h);
				}

				int[] newTimes = new int[framesCount - 1];
				int lastTime = time;

				for (int i = 0; i < framesCount - 1; i++) {
					if (i < times.length) {
						newTimes[i] = times[i];
						lastTime = times[i];
					} else
						newTimes[i] = lastTime;
				}

				FrameAnimationImpl frameAnimation = new FrameAnimationImpl(time, newTimes);
				frameAnimation.setLoop(loop);

				return new Animation(frames, frameAnimation);
			}

		}));
	}

	public void font(String id, String imageFile, String fontFile) {
		font(id, imageFile, fontFile, false);
	}

	public void font(String id, final String imageFile, final String fontFile, final boolean linearFilter) {
		resourceManager.add(id, new CachedResourceLoader<BitmapFont>(new ResourceLoaderImpl<BitmapFont>(new DisposableDataLoader<BitmapFont>(internal(imageFile)) {
			@Override
			public BitmapFont load() {
				Texture texture = new Texture(internal(imageFile));
				if (linearFilter)
					texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				return new BitmapFont(internal(fontFile), new Sprite(texture), false);
			}
		})));
	}

	public void sound(String id, String file) {
		sound(id, internal(file));
	}

	public void sound(String id, FileHandle fileHandle) {
		resourceManager.add(id, new CachedResourceLoader<Sound>(new ResourceLoaderImpl<Sound>(new SoundDataLoader(fileHandle))));
		if (cacheWhenLoad)
			resourceManager.get(id).get();
	}

	public void music(String id, String file) {
		resourceManager.add(id, new CachedResourceLoader<Music>(new ResourceLoaderImpl<Music>(new DisposableDataLoader<Music>(internal(file)) {
			@Override
			public Music load() {
				return Gdx.audio.newMusic(fileHandle);
			}

			@Override
			public void dispose(Music t) {
				if (t.isPlaying())
					t.stop();
				super.dispose(t);
			}
		})));
	}

	public void xmlDocument(String id, final String file) {
		resourceManager.add(id, new CachedResourceLoader<Document>(new ResourceLoaderImpl<Document>(new DataLoader<Document>() {
			@Override
			public Document load() {
				return new DocumentParser().parse(internal(file).read());
			}
		})));
	}

	// / TESTING STUFF

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void resource(String id, final ResourceBuilder resourceBuilder) {

		ResourceLoader resourceLoader = new ResourceLoaderImpl(new DataLoader() {
			@Override
			public Object load() {
				return resourceBuilder.build();
			}
		});

		if (resourceBuilder.isCached())
			resourceLoader = new CachedResourceLoader(resourceLoader);

		resourceManager.add(id, resourceLoader);
	}

	public SpriteResourceBuilder sprite2(String textureId) {
		Texture texture = resourceManager.getResourceValue(textureId);
		return new SpriteResourceBuilder(texture);
	}

	public AnimationResourceBuilder animation2(String textureId) {
		return new AnimationResourceBuilder(resourceManager, textureId);
	}

	public XmlDocumentDeclaration xmlDocument(String file) {
		return new XmlDocumentDeclaration(file);
	}

	public static class XmlDocumentDeclaration implements ResourceBuilder<Document> {

		private final String file;

		private boolean cached = false;

		private FileType fileType = FileType.Internal;

		public XmlDocumentDeclaration fileType(FileType fileType) {
			this.fileType = fileType;
			return this;
		}

		public XmlDocumentDeclaration cached() {
			this.cached = true;
			return this;
		}

		public XmlDocumentDeclaration(String file) {
			this.file = file;
		}

		@Override
		public Document build() {
			return new DocumentParser().parse(Gdx.files.getFileHandle(file, fileType).read());
		}

		@Override
		public boolean isCached() {
			return cached;
		}

	}

}