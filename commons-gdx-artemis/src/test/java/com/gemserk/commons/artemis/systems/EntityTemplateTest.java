package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.gemserk.commons.artemis.Script;
import com.gemserk.commons.artemis.ScriptJavaImpl;
import com.gemserk.commons.artemis.components.ScriptComponent;
import com.gemserk.commons.artemis.components.SpatialComponent;
import com.gemserk.commons.gdx.games.Spatial;
import com.gemserk.commons.gdx.games.SpatialImpl;
import com.gemserk.componentsengine.utils.Container;
import com.gemserk.componentsengine.utils.ParametersWrapper;

public class EntityTemplateTest {

	class HealthComponent extends Component {

		Container health;

		public Container getHealth() {
			return health;
		}

		public HealthComponent(Container health) {
			this.health = health;
		}

	}

	class DamageComponent extends Component {

		float damage;

		public float getDamage() {
			return damage;
		}

		public DamageComponent(float damage) {
			this.damage = damage;
		}

	}

	class WeaponComponent extends Component {

		float damage;

		EntityTemplate bulletTemplate;

		public EntityTemplate getBulletTemplate() {
			return bulletTemplate;
		}

		public float getDamage() {
			return damage;
		}

		public WeaponComponent(float damage, EntityTemplate bulletTemplate) {
			this.damage = damage;
			this.bulletTemplate = bulletTemplate;
		}

	}

	interface EntityTemplate {

		ParametersWrapper getDefaultParameters();

		void apply(Entity entity);

		void apply(Entity entity, ParametersWrapper parameters);

	}

	class ShipEntityTemplate implements EntityTemplate {

		ParametersWrapper parameters = new ParametersWrapper();

		public ShipEntityTemplate() {
			parameters.setWrappedParameters(new HashMap<String, Object>());
			parameters.put("health", new Container(100f, 100f));
		}

		@Override
		public void apply(Entity entity) {
			apply(entity, parameters);
		}

		@Override
		public ParametersWrapper getDefaultParameters() {
			return parameters;
		}

		@Override
		public void apply(Entity entity, ParametersWrapper parameters) {
			Float x = parameters.get("x", 0f);
			Float y = parameters.get("y", 0f);

			Container health = parameters.get("health");

			entity.addComponent(new SpatialComponent(new SpatialImpl(x, y, 1, 1, 0f)));
			entity.addComponent(new HealthComponent(new Container(health.getCurrent(), health.getTotal())));
		}

	}

	class WeaponEntityTemplate implements EntityTemplate {

		ParametersWrapper parameters = new ParametersWrapper();

		public WeaponEntityTemplate() {
			parameters.setWrappedParameters(new HashMap<String, Object>());
			parameters.put("damage", new Float(5f));
			parameters.put("script", new ScriptJavaImpl());
		}

		@Override
		public void apply(Entity entity) {
			apply(entity, parameters);
		}

		@Override
		public ParametersWrapper getDefaultParameters() {
			return parameters;
		}

		@Override
		public void apply(Entity entity, ParametersWrapper parameters) {
			Float damage = parameters.get("damage");
			EntityTemplate bulletTemplate = parameters.get("bulletTemplate");
			Script script = parameters.get("script");
			entity.addComponent(new WeaponComponent(damage, bulletTemplate));
			entity.addComponent(new ScriptComponent(script));
		}

	}

	class BulletEntityTemplate implements EntityTemplate {

		ParametersWrapper parameters = new ParametersWrapper();

		public BulletEntityTemplate() {
			parameters.setWrappedParameters(new HashMap<String, Object>());
			parameters.put("damage", new Float(5f));
		}

		@Override
		public void apply(Entity entity) {
			apply(entity, parameters);
		}

		@Override
		public ParametersWrapper getDefaultParameters() {
			return parameters;
		}

		@Override
		public void apply(Entity entity, ParametersWrapper parameters) {
			Float damage = parameters.get("damage");
			entity.addComponent(new DamageComponent(damage));
		}

	}

	interface EntityFactory {

		Entity instantiate(EntityTemplate template);

		Entity instantiate(EntityTemplate template, ParametersWrapper parameters);

	}

	class EntityFactoryImpl implements EntityFactory {

		World world;

		public EntityFactoryImpl(World world) {
			this.world = world;
		}

		@Override
		public Entity instantiate(EntityTemplate template) {
			return instantiate(template, template.getDefaultParameters());
		}

		@Override
		public Entity instantiate(EntityTemplate template, ParametersWrapper parameters) {
			Entity entity = world.createEntity();
			template.apply(entity, parameters);
			entity.refresh();
			return entity;
		}

	}

	@Test
	public void test() {

		EntityTemplate customShipTemplate = new ShipEntityTemplate() {
			{
				parameters.put("x", 100f);
				parameters.put("y", 200f);
				parameters.put("health", new Container(53f, 250f));
			}
		};

		EntityFactory entityFactory = new EntityFactoryImpl(new World());

		Entity entity = entityFactory.instantiate(customShipTemplate);

		SpatialComponent spatialComponent = entity.getComponent(SpatialComponent.class);
		Spatial spatial = spatialComponent.getSpatial();

		assertThat(spatial.getX(), IsEqual.equalTo(100f));
		assertThat(spatial.getY(), IsEqual.equalTo(200f));

		HealthComponent healthComponent = entity.getComponent(HealthComponent.class);
		Container health = healthComponent.getHealth();

		assertThat(health.getCurrent(), IsEqual.equalTo(53f));
		assertThat(health.getTotal(), IsEqual.equalTo(250f));

	}

	@Test
	public void testModifyPositionByHand() {

		// default parameters through a custom template, could be created when the level starts with custom level information
		EntityTemplate customShipTemplate = new ShipEntityTemplate() {
			{
				parameters.put("health", new Container(250f, 250f));
			}
		};

		EntityFactory entityFactory = new EntityFactoryImpl(new World());

		Entity entity = entityFactory.instantiate(customShipTemplate);

		SpatialComponent spatialComponent = entity.getComponent(SpatialComponent.class);
		Spatial spatial = spatialComponent.getSpatial();

		spatial.setPosition(100f, 100f);

	}

	@Test
	public void weaponInstantiateBullet() {

		EntityTemplate bulletTemplate = new BulletEntityTemplate();

		// modifies the template forever, if we are using the same template in different weapons or something similar, then they will be modified as well.
		bulletTemplate.getDefaultParameters().put("damage", new Float(200f));

		EntityFactory entityFactory = new EntityFactoryImpl(new World());
		Entity bullet = entityFactory.instantiate(bulletTemplate);

		DamageComponent damageComponent = bullet.getComponent(DamageComponent.class);
		assertThat(damageComponent.getDamage(), IsEqual.equalTo(200f));

	}

	@Test
	public void weaponInstantiateBulletWithWeaponParameters() {

		// get the component from the weapon (the current entity)
		WeaponComponent weaponComponent = new WeaponComponent(560f, null);

		// custom template parameters of the weapon to be used when building a new bullet
		// this parameters are stored in each weapon instance.
		ParametersWrapper weaponBulletParameters = new ParametersWrapper();
		weaponBulletParameters.put("damage", weaponComponent.getDamage());

		// the interesting part here is that I could change it dynamically without changing the template or the instantiation call

		EntityTemplate bulletTemplate = new BulletEntityTemplate();

		EntityFactory entityFactory = new EntityFactoryImpl(new World());
		Entity bullet = entityFactory.instantiate(bulletTemplate, weaponBulletParameters);

		DamageComponent damageComponent = bullet.getComponent(DamageComponent.class);
		assertThat(damageComponent.getDamage(), IsEqual.equalTo(560f));

	}

	@Test
	public void weaponInstantiateBulletWithWeaponParametersWithStyle() {
		World world = new World();
		
		final EntityFactory entityFactory = new EntityFactoryImpl(world);

		EntityTemplate weaponTemplate = new WeaponEntityTemplate();
		EntityTemplate bulletTemplate = new BulletEntityTemplate();

		ParametersWrapper weaponParameters = new ParametersWrapper();
		weaponParameters.put("damage", 30f);
		weaponParameters.put("bulletTemplate", bulletTemplate);
		weaponParameters.put("script", new ScriptJavaImpl() {
			
			ParametersWrapper bulletParameters = new ParametersWrapper();
			
			@Override
			public void update(World world, Entity e) {
				WeaponComponent weaponComponent = e.getComponent(WeaponComponent.class);
				bulletParameters.put("damage", weaponComponent.getDamage());
				
				EntityTemplate bulletTemplate = weaponComponent.getBulletTemplate();
				Entity bullet = entityFactory.instantiate(bulletTemplate, bulletParameters);
				
				DamageComponent damageComponent = bullet.getComponent(DamageComponent.class);
				assertThat(damageComponent.getDamage(), IsEqual.equalTo(30f));
			}
		});
		
		Entity weapon = entityFactory.instantiate(weaponTemplate, weaponParameters);
		
		// ... on script system
		
		ScriptComponent scriptComponent = weapon.getComponent(ScriptComponent.class);
		scriptComponent.getScript().update(world, weapon);

	}

}
