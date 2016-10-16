package id.co.motion.research.component;

import javax.inject.Singleton;

import dagger.Component;
import id.co.motion.research.fragment.HomeFragment;
import id.co.motion.research.module.AppModule;
import id.co.motion.research.module.DbModule;

@Singleton
@Component(modules = {AppModule.class, DbModule.class})
public interface ResearchComponent {
    void inject(HomeFragment fragment);
}
