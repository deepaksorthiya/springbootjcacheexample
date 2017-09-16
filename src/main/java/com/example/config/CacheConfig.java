package com.example.config;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.common.SampleConstant;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {

	final static String CACHE_POLICY = "LRU";

	@Bean(destroyMethod = "shutdown")
	public net.sf.ehcache.CacheManager ehCacheManager() {
		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(cacheConfig(SampleConstant.SAMPLE_CACHE, 150, 1500));
		config.addCache(cacheConfig(SampleConstant.SAMPLE_DETAIL_CACHE, 500, 500));
		return net.sf.ehcache.CacheManager.newInstance(config);
	}

	@Bean
	@Override
	public org.springframework.cache.CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	private CacheConfiguration cacheConfig(String name, long maxEntries, long timeToLive) {
		CacheConfiguration config = new CacheConfiguration();
		config.setName(name);
		config.setMaxEntriesLocalHeap(maxEntries);
		config.setTimeToLiveSeconds(timeToLive);
		config.setMemoryStoreEvictionPolicy(CACHE_POLICY);
		return config;
	}

	@Bean
	@Override
	public CacheResolver cacheResolver() {
		return null;
	}

	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		return null;
	}
}
