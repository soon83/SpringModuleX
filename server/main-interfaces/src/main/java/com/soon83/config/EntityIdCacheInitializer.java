package com.soon83.config;

import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityIdCacheInitializer {
    private static final Map<Class<?>, String> idFieldCache = new ConcurrentHashMap<>();
    private final EntityManager entityManager;

    public static String getCachedIdFieldName(Class<?> entityClass) {
        return idFieldCache.get(entityClass);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initEntityIdCache() {
        Set<Class<?>> entityClasses = getAllEntityClasses(); // 엔티티 클래스 목록 가져오기
        for (Class<?> entityClass : entityClasses) {
            String idFieldName = getIdColumnName(entityClass);
            idFieldCache.put(entityClass, idFieldName);  // Map에 캐싱
            log.debug("Cached @Id field for entity: {} -> {}", entityClass.getSimpleName(), idFieldName);
        }
    }

    // JPA EntityManager 에서 모든 엔티티 클래스 가져오기
    private Set<Class<?>> getAllEntityClasses() {
        return entityManager.getMetamodel().getEntities().stream()
                .map(EntityType::getJavaType)
                .collect(Collectors.toSet());
    }

    // @Id 필드의 이름을 찾아 반환
    private String getIdColumnName(Class<?> entityClass) {
        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(jakarta.persistence.Id.class))
                .findFirst()
                .map(field -> {
                    Column column = field.getAnnotation(Column.class);
                    return column != null ? column.name() : field.getName().toUpperCase();
                })
                .orElseThrow(() -> new IllegalArgumentException("No @Id field found in class " + entityClass.getName()));
    }
}
