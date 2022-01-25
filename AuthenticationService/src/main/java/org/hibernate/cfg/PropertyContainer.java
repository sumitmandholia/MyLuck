/*     */ package org.hibernate.cfg;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.LinkedHashMap;
/*     */ import javax.persistence.Access;
/*     */ import javax.persistence.ManyToMany;
/*     */ import javax.persistence.ManyToOne;
/*     */ import javax.persistence.OneToMany;
/*     */ import javax.persistence.OneToOne;
/*     */ import javax.persistence.Transient;
/*     */ import org.hibernate.AnnotationException;
/*     */ import org.hibernate.annotations.Any;
/*     */ import org.hibernate.annotations.ManyToAny;
/*     */ import org.hibernate.annotations.Target;
/*     */ import org.hibernate.annotations.Type;
/*     */ import org.hibernate.annotations.common.reflection.XClass;
/*     */ import org.hibernate.annotations.common.reflection.XProperty;
/*     */ import org.hibernate.boot.MappingException;
/*     */ import org.hibernate.boot.jaxb.Origin;
/*     */ import org.hibernate.boot.jaxb.SourceType;
/*     */ import org.hibernate.cfg.annotations.HCANNHelper;
/*     */ import org.hibernate.internal.CoreMessageLogger;
/*     */ import org.hibernate.internal.util.StringHelper;
/*     */ import org.hibernate.internal.util.collections.CollectionHelper;
/*     */ import org.jboss.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PropertyContainer {
/*     */    private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PropertyContainer.class.getName());
/*     */    private final XClass xClass;
/*     */    private final XClass entityAtStake;
/*     */    private final AccessType classLevelAccessType;
/*     */    private final List<XProperty> persistentAttributes;
/*     */ 
/*     */    PropertyContainer(XClass clazz, XClass entityAtStake, AccessType defaultClassLevelAccessType) {
/*  71 */       this.xClass = clazz;
/*  72 */       this.entityAtStake = entityAtStake;
/*     */ 
/*  74 */       if (defaultClassLevelAccessType == AccessType.DEFAULT) {
/*     */ 
/*     */ 
/*  77 */          defaultClassLevelAccessType = AccessType.PROPERTY;
/*     */       }
/*     */ 
/*  80 */       AccessType localClassLevelAccessType = this.determineLocalClassDefinedAccessStrategy();
/*  81 */       assert localClassLevelAccessType != null;
/*     */ 
/*  83 */       this.classLevelAccessType = localClassLevelAccessType != AccessType.DEFAULT ? localClassLevelAccessType : defaultClassLevelAccessType;
/*     */ 
/*     */ 
/*  86 */       assert this.classLevelAccessType == AccessType.FIELD || this.classLevelAccessType == AccessType.PROPERTY;
/*     */ 
/*     */ 
/*  89 */       List<XProperty> fields = this.xClass.getDeclaredProperties(AccessType.FIELD.getType());
/*  90 */       List<XProperty> getters = this.xClass.getDeclaredProperties(AccessType.PROPERTY.getType());
/*     */ 
/*  92 */       this.preFilter(fields, getters);
/*     */ 
/*  94 */       Map<String, XProperty> persistentAttributesFromGetters = new HashMap();
/*     */ 
/*  96 */       LinkedHashMap<String, XProperty> localAttributeMap = new LinkedHashMap();
/*  97 */       collectPersistentAttributesUsingLocalAccessType(this.xClass, localAttributeMap, persistentAttributesFromGetters, fields, getters);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */       collectPersistentAttributesUsingClassLevelAccessType(this.xClass, this.classLevelAccessType, localAttributeMap, persistentAttributesFromGetters, fields, getters);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 112 */       this.persistentAttributes = verifyAndInitializePersistentAttributes(this.xClass, localAttributeMap);
/* 113 */    }
/*     */ 
/*     */    private void preFilter(List<XProperty> fields, List<XProperty> getters) {
/* 116 */       Iterator propertyIterator = fields.iterator();      XProperty property;
/* 117 */       while(propertyIterator.hasNext()) {
/* 118 */          property = (XProperty)propertyIterator.next();
/* 119 */          if (mustBeSkipped(property)) {
/* 120 */             propertyIterator.remove();
/*     */          }
/*     */       }
/*     */ 
/* 124 */       propertyIterator = getters.iterator();
/* 125 */       while(propertyIterator.hasNext()) {
/* 126 */          property = (XProperty)propertyIterator.next();
/* 127 */          if (mustBeSkipped(property)) {
/* 128 */             propertyIterator.remove();
/*     */          }      }
/*     */ 
/* 131 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private static void collectPersistentAttributesUsingLocalAccessType(XClass xClass, LinkedHashMap<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, List<XProperty> fields, List<XProperty> getters) {
/* 141 */       Iterator propertyIterator = fields.iterator();      XProperty xProperty;      Access localAccessAnnotation;
/* 142 */       while(propertyIterator.hasNext()) {
/* 143 */          xProperty = (XProperty)propertyIterator.next();
/* 144 */          localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
/* 145 */          if (localAccessAnnotation != null && localAccessAnnotation.value() == javax.persistence.AccessType.FIELD) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 150 */             propertyIterator.remove();
/* 151 */             persistentAttributeMap.put(xProperty.getName(), xProperty);
/*     */          }
/*     */       }
/*     */ 
/* 155 */       propertyIterator = getters.iterator();
/* 156 */       while(propertyIterator.hasNext()) {
/* 157 */          xProperty = (XProperty)propertyIterator.next();
/* 158 */          localAccessAnnotation = (Access)xProperty.getAnnotation(Access.class);
/* 159 */          if (localAccessAnnotation != null && localAccessAnnotation.value() == javax.persistence.AccessType.PROPERTY) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 164 */             propertyIterator.remove();
/*     */ 
/* 166 */             String name = xProperty.getName();
/*     */ 
/*     */ 
/* 169 */             XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
/* 170 */             if (previous != null) {
/* 171 */                throw new MappingException(LOG.ambiguousPropertyMethods(xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(xProperty)), new Origin(SourceType.ANNOTATION, xClass.getName()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             }
/*     */ 
/* 181 */             persistentAttributeMap.put(name, xProperty);
/* 182 */             persistentAttributesFromGetters.put(name, xProperty);
/*     */          }      }
/* 184 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private static void collectPersistentAttributesUsingClassLevelAccessType(XClass xClass, AccessType classLevelAccessType, LinkedHashMap<String, XProperty> persistentAttributeMap, Map<String, XProperty> persistentAttributesFromGetters, List<XProperty> fields, List<XProperty> getters) {
/*     */       Iterator var6;
/*     */       XProperty getter;
/* 193 */       if (classLevelAccessType == AccessType.FIELD) {
/* 194 */          var6 = fields.iterator();         while(var6.hasNext()) {            getter = (XProperty)var6.next();
/* 195 */             if (!persistentAttributeMap.containsKey(getter.getName())) {
/*     */ 
/*     */ 
/*     */ 
/* 199 */                persistentAttributeMap.put(getter.getName(), getter);
/*     */             }
/*     */          }
/*     */       } else {
/* 203 */          var6 = getters.iterator();         while(var6.hasNext()) {            getter = (XProperty)var6.next();
/* 204 */             String name = getter.getName();
/*     */ 
/*     */ 
/* 207 */             XProperty previous = (XProperty)persistentAttributesFromGetters.get(name);
/* 208 */             if (previous != null) {
/* 209 */                throw new MappingException(LOG.ambiguousPropertyMethods(xClass.getName(), HCANNHelper.annotatedElementSignature(previous), HCANNHelper.annotatedElementSignature(getter)), new Origin(SourceType.ANNOTATION, xClass.getName()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             }
/*     */ 
/* 219 */             if (!persistentAttributeMap.containsKey(name)) {
/*     */ 
/*     */ 
/*     */ 
/* 223 */                persistentAttributeMap.put(getter.getName(), getter);
/* 224 */                persistentAttributesFromGetters.put(name, getter);
/*     */             }         }      }
/*     */ 
/* 227 */    }
/*     */ 
/*     */    public XClass getEntityAtStake() {
/* 230 */       return this.entityAtStake;
/*     */    }
/*     */ 
/*     */    public XClass getDeclaringClass() {
/* 234 */       return this.xClass;
/*     */    }
/*     */ 
/*     */    public AccessType getClassLevelAccessType() {
/* 238 */       return this.classLevelAccessType;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    @Deprecated
/*     */    public Collection<XProperty> getProperties() {
/* 246 */       return Collections.unmodifiableCollection(this.persistentAttributes);
/*     */    }
/*     */ 
/*     */    public Iterable<XProperty> propertyIterator() {
/* 250 */       return this.persistentAttributes;
/*     */    }
/*     */ 
/*     */    private static List<XProperty> verifyAndInitializePersistentAttributes(XClass xClass, Map<String, XProperty> localAttributeMap) {
/* 254 */       ArrayList<XProperty> output = new ArrayList(localAttributeMap.size());      Iterator var3 = localAttributeMap.values().iterator();      while(var3.hasNext()) {
/* 255 */          XProperty xProperty = (XProperty)var3.next();
/* 256 */          if (!xProperty.isTypeResolved() && !discoverTypeWithoutReflection(xProperty)) {
/* 257 */             String msg = "Property " + StringHelper.qualify(xClass.getName(), xProperty.getName()) + " has an unbound type and no explicit target entity. Resolve this Generic usage issue or set an explicit target attribute (eg @OneToMany(target=) or use an explicit @Type";
/*     */ 
/*     */ 
/* 260 */             throw new AnnotationException(msg);
/*     */          }
/* 262 */          output.add(xProperty);
/*     */       }
/* 264 */       return CollectionHelper.toSmallList(output);
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private AccessType determineLocalClassDefinedAccessStrategy() {
/* 347 */       AccessType hibernateDefinedAccessType = AccessType.DEFAULT;
/* 348 */       AccessType jpaDefinedAccessType = AccessType.DEFAULT;
/*     */ 
/* 350 */       org.hibernate.annotations.AccessType accessType = (org.hibernate.annotations.AccessType)this.xClass.getAnnotation(org.hibernate.annotations.AccessType.class);
/* 351 */       if (accessType != null) {
/* 352 */          hibernateDefinedAccessType = AccessType.getAccessStrategy(accessType.value());
/*     */       }
/*     */ 
/* 355 */       Access access = (Access)this.xClass.getAnnotation(Access.class);
/* 356 */       if (access != null) {
/* 357 */          jpaDefinedAccessType = AccessType.getAccessStrategy(access.value());
/*     */       }
/*     */ 
/* 360 */       if (hibernateDefinedAccessType != AccessType.DEFAULT && jpaDefinedAccessType != AccessType.DEFAULT && hibernateDefinedAccessType != jpaDefinedAccessType) {
/*     */ 
/*     */ 
/* 363 */          throw new org.hibernate.MappingException("@AccessType and @Access specified with contradicting values. Use of @Access only is recommended. ");
/*     */ 
/*     */ 
/*     */       } else {
/*     */          AccessType classDefinedAccessType;
/* 368 */          if (hibernateDefinedAccessType != AccessType.DEFAULT) {
/* 369 */             classDefinedAccessType = hibernateDefinedAccessType;
/*     */ 
/*     */          } else {
/* 372 */             classDefinedAccessType = jpaDefinedAccessType;
/*     */          }
/* 374 */          return classDefinedAccessType;
/*     */       }
/*     */    }
/*     */    private static boolean discoverTypeWithoutReflection(XProperty p) {
/* 378 */       if (p.isAnnotationPresent(OneToOne.class) && !((OneToOne)p.getAnnotation(OneToOne.class)).targetEntity().equals(Void.TYPE)) {
/*     */ 
/*     */ 
/* 381 */          return true;
/*     */ 
/* 383 */       } else if (p.isAnnotationPresent(OneToMany.class) && !((OneToMany)p.getAnnotation(OneToMany.class)).targetEntity().equals(Void.TYPE)) {
/*     */ 
/*     */ 
/* 386 */          return true;
/*     */ 
/* 388 */       } else if (p.isAnnotationPresent(ManyToOne.class) && !((ManyToOne)p.getAnnotation(ManyToOne.class)).targetEntity().equals(Void.TYPE)) {
/*     */ 
/*     */ 
/* 391 */          return true;
/*     */ 
/* 393 */       } else if (p.isAnnotationPresent(ManyToMany.class) && !((ManyToMany)p.getAnnotation(ManyToMany.class)).targetEntity().equals(Void.TYPE)) {
/*     */ 
/*     */ 
/* 396 */          return true;
/*     */ 
/* 398 */       } else if (p.isAnnotationPresent(Any.class)) {
/* 399 */          return true;
/*     */ 
/* 401 */       } else if (p.isAnnotationPresent(ManyToAny.class)) {
/* 402 */          if (!p.isCollection() && !p.isArray()) {
/* 403 */             throw new AnnotationException("@ManyToAny used on a non collection non array property: " + p.getName());
/*     */          } else {
/* 405 */             return true;
/*     */          }
/* 407 */       } else if (p.isAnnotationPresent(Type.class)) {
/* 408 */          return true;
/*     */       } else {
/* 410 */          return p.isAnnotationPresent(Target.class);
/*     */       }
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    private static boolean mustBeSkipped(XProperty property) {
/* 418 */       return property.isAnnotationPresent(Transient.class) || "net.sf.cglib.transform.impl.InterceptFieldCallback".equals(property.getType().getName());
/*     */    }
/*     */ }
