/*
 * Copyright 2014 The Closure Compiler Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.javascript.jscomp.newtypes;

import com.google.common.collect.ImmutableSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Static methods that operate on {@code TypeWithProperties} instances.
 */
final class TypeWithPropertiesStatics {

  // Never create any instances of this class
  private TypeWithPropertiesStatics() {}

  static JSType getProp(
      ImmutableSet<? extends TypeWithProperties> types, QualifiedName qname) {
    if (types == null) {
      return null;
    }
    JSType ptype = null;
    for (TypeWithProperties t : types) {
      if (t.mayHaveProp(qname)) {
        JSType tmp = t.getProp(qname);
        ptype = ptype == null ? tmp : JSType.join(ptype, tmp);
      }
    }
    return ptype;
  }

  static JSType getDeclaredProp(
      ImmutableSet<? extends TypeWithProperties> types, QualifiedName qname) {
    if (types == null) {
      return null;
    }
    JSType ptype = null;
    for (TypeWithProperties t : types) {
      if (t.mayHaveProp(qname)) {
        JSType declType = t.getDeclaredProp(qname);
        if (declType != null) {
          ptype = ptype == null ? declType : JSType.join(ptype, declType);
        }
      }
    }
    return ptype;
  }

  static Collection<JSType> getSubtypesWithProperty(
      ImmutableSet<? extends TypeWithProperties> types, QualifiedName qname) {
    if (types == null) {
      return ImmutableSet.of();
    }
    Set<JSType> typesWithProp = new HashSet<>();
    for (TypeWithProperties t : types) {
      typesWithProp.addAll(t.getSubtypesWithProperty(qname));
    }
    return typesWithProp;
  }

  static boolean mayHaveProp(
      ImmutableSet<? extends TypeWithProperties> types, QualifiedName qname) {
    if (types == null) {
      return false;
    }
    for (TypeWithProperties t : types) {
      if (t.mayHaveProp(qname)) {
        return true;
      }
    }
    return false;
  }

  static boolean hasProp(
      ImmutableSet<? extends TypeWithProperties> types, QualifiedName qname) {
    if (types == null) {
      return false;
    }
    for (TypeWithProperties t : types) {
      if (!t.hasProp(qname)) {
        return false;
      }
    }
    return true;
  }

  static boolean hasConstantProp(
      ImmutableSet<? extends TypeWithProperties> types, QualifiedName qname) {
    if (types == null) {
      return false;
    }
    for (TypeWithProperties t : types) {
      if (t.hasConstantProp(qname)) {
        return true;
      }
    }
    return false;
  }
}
