/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2008-present Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.orient.entity;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.Entity;
import org.sonatype.nexus.orient.entity.action.BrowseEntitiesAction;
import org.sonatype.nexus.orient.entity.action.CountDocumentsAction;
import org.sonatype.nexus.orient.entity.action.ReadEntityByIdAction;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Iterable records entity-adapter.
 *
 * @since 3.0
 */
public abstract class IterableEntityAdapter<T extends Entity>
    extends EntityAdapter<T>
{
  public IterableEntityAdapter(final String typeName) {
    super(typeName);
  }

  /**
   * Transform documents into entities.
   */
  public Iterable<T> transform(final Iterable<ODocument> documents) {
    return Iterables.transform(documents, new Function<ODocument, T>()
    {
      @Nullable
      @Override
      public T apply(@Nullable final ODocument input) {
        return input != null ? readEntity(input) : null;
      }
    });
  }

  //
  // Actions
  //

  public final ReadEntityByIdAction<T> read = new ReadEntityByIdAction<>(this);

  public final BrowseEntitiesAction<T> browse = new BrowseEntitiesAction<>(this);

  public final CountDocumentsAction count = new CountDocumentsAction(this);
}
