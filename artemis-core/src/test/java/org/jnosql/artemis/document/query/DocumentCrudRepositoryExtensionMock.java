/*
 * Copyright 2017 Otavio Santana and others
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jnosql.artemis.document.query;

import org.jnosql.artemis.CrudRepository;
import org.jnosql.artemis.document.query.CrudRepositoryDocumentBean;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import java.util.Collection;
import java.util.HashSet;


public class DocumentCrudRepositoryExtensionMock implements Extension {


    private final Collection<Class<?>> types = new HashSet<>();

    <T extends CrudRepository> void onProcessAnnotatedType(@Observes final ProcessAnnotatedType<T> repo) {

        Class<T> javaClass = repo.getAnnotatedType().getJavaClass();
        types.add(javaClass);
    }

    void onAfterBeanDiscovery(@Observes final AfterBeanDiscovery afterBeanDiscovery, final BeanManager beanManager) {
        types.forEach(t -> {
            final CrudRepositoryDocumentBean bean = new CrudRepositoryDocumentBean(t, beanManager, "documentRepositoryMock");
            afterBeanDiscovery.addBean(bean);
        });
    }
}