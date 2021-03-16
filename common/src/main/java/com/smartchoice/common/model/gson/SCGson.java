package com.smartchoice.common.model.gson;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.smartchoice.common.util.TimeUtil;

public class SCGson {

	private static final Logger log = LogManager.getLogger(SCGson.class);

	public enum GsonAdapter {
		/**
		 * HibernateProxyTypeAdapter <br>
		 */
		PROXY,

		/**
		 * IncludeWithoutSuppressStrategy <br>
		 */
		SUPPRESS,

		/**
		 * GenericEnumAdapterFactory <br>
		 */
		GENERIC_ENUM,

		/**
		 * TimeUtil.CF.ISO_8601 <br>
		 */
		ISO_8601,

		/**
		 * TimeUtil.CF.ISO_8601_NO_MILLI <br>
		 */
		ISO_8601_NO_MILLI,

		/**
		 * serializeNulls <br>
		 */
		SERIALIZE_NULLS,

	}

	private static ConcurrentHashMap<Set<GsonAdapter>, Gson> gsonMap = new ConcurrentHashMap<>();

	public static Gson getGson(GsonAdapter... gsonAdapter) {
		Set<GsonAdapter> setAdapter = Set.of(gsonAdapter);
		if (gsonMap.containsKey(setAdapter)) {
			return gsonMap.get(setAdapter);
		} else {
			GsonBuilder builder = new GsonBuilder();
			for (GsonAdapter adapter : setAdapter) {
				appendBuilder(adapter, builder);
			}
			Gson gson = builder.create();
			gsonMap.put(setAdapter, gson);
			log.debug("Gson map size {}", gsonMap.size());
			return gson;
		}
	}

	public static GsonBuilder getBuilder(GsonAdapter... gsonAdapter) {
		GsonBuilder builder = new GsonBuilder();
		Set<GsonAdapter> setAdapter = Set.of(gsonAdapter);
		for (GsonAdapter adapter : setAdapter) {
			appendBuilder(adapter, builder);
		}
		return builder;
	}

	public static void appendBuilder(GsonAdapter adapter, GsonBuilder builder) {
		switch (adapter) {
		case PROXY:
			builder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
			break;
		case SUPPRESS:
			builder.setExclusionStrategies(new IncludeWithoutSuppressStrategy());
			break;
		case GENERIC_ENUM:
			builder.registerTypeAdapterFactory(new GenericEnumAdapterFactory());
			break;
		case ISO_8601:
			builder.registerTypeAdapter(LocalDateTime.class,
					new GsonLocalDateTimeSerializer(TimeUtil.CF.ISO_8601.getPrinter())).registerTypeAdapter(
							LocalDateTime.class, new GsonZdtToLdtUTCDeserializer(TimeUtil.CF.ISO_8601.getParser()));
			break;
		case ISO_8601_NO_MILLI:
			builder.registerTypeAdapter(LocalDateTime.class,
					new GsonZdtToLdtUTCDeserializer(TimeUtil.CF.ISO_8601_NO_MILLI.getParser()))
					.registerTypeAdapter(LocalDateTime.class,
							new GsonLocalDateTimeSerializer(TimeUtil.CF.ISO_8601_NO_MILLI.getPrinter()));
			break;
		case SERIALIZE_NULLS:
			builder.serializeNulls();
			break;
		default:
			break;
		}
	}
}
