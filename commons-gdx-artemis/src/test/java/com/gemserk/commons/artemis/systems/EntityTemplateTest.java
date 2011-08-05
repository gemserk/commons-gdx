package com.gemserk.commons.artemis.systems;

import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
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
	
	interface EntityTemplate {
		
		void apply(Entity entity);
		
	}
	
	class ShipEntityTemplate implements EntityTemplate {
		
		ParametersWrapper parameters = new ParametersWrapper();
		
		public ShipEntityTemplate() {
			parameters.setWrappedParameters(new HashMap<String, Object>());
			parameters.put("health", new Container(100f, 100f));
		}
		
		@Override
		public void apply(Entity entity) {
			float x = parameters.get("x", 0f);
			float y = parameters.get("y", 0f);
			
			Container health = parameters.get("health");
			
			entity.addComponent(new SpatialComponent(new SpatialImpl(x, y, 1, 1, 0f)));
			entity.addComponent(new HealthComponent(new Container(health.getCurrent(), health.getTotal())));
		}
		
	}
	
	interface EntityFactory {
		
		Entity instantiate(EntityTemplate template);
		
	}
	
	class EntityFactoryImpl implements EntityFactory {
		
		World world;
	
		public EntityFactoryImpl(World world) {
			this.world = world;
		}

		@Override
		public Entity instantiate(EntityTemplate template) {
			Entity entity = world.createEntity();
			template.apply(entity);
			entity.refresh();
			return entity;
		} 
		
	}
	
	@Test
	public void test() {

		EntityTemplate customShipTemplate = new ShipEntityTemplate() {{
			parameters.put("x", 100f);
			parameters.put("y", 200f);
			parameters.put("health", new Container(53f, 250f));
		}};
		
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
	public void test2() {

		// parametros por defecto mediante un custom template, puede ser creado al comienzo del nivel con datos del nivel.
		EntityTemplate customShipTemplate = new ShipEntityTemplate() {{
			parameters.put("health", new Container(250f, 250f));
		}};
		
		EntityFactory entityFactory = new EntityFactoryImpl(new World());
		
		Entity entity = entityFactory.instantiate(customShipTemplate);
		
		SpatialComponent spatialComponent = entity.getComponent(SpatialComponent.class);
		Spatial spatial = spatialComponent.getSpatial();
		
		spatial.setPosition(100f, 100f);
		
	}

}
