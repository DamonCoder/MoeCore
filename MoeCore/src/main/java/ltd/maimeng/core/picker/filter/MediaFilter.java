/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ltd.maimeng.core.picker.filter;

import android.content.Context;

import ltd.maimeng.core.picker.MimeType;
import ltd.maimeng.core.picker.bean.IncapableCause;
import ltd.maimeng.core.picker.bean.MediaItem;

import java.util.Set;


/**
 * Filter for choosing a {@link Item}. You can add multiple Filters through
 * {@link SelectionCreator#addFilter(Filter)}.
 */
@SuppressWarnings("unused")
public abstract class MediaFilter {
    /**
     * Convenient constant for a minimum value.
     */
    public static final int MIN = 0;
    /**
     * Convenient constant for a maximum value.
     */
    public static final int MAX = Integer.MAX_VALUE;
    /**
     * Convenient constant for 1024.
     */
    public static final int K = 1024;

    /**
     * Against what mime types this filter applies.
     */
    protected abstract Set<MimeType> constraintTypes();

    /**
     * Invoked for filtering each item.
     *
     * @return null if selectable, {@link IncapableCause} if not selectable.
     */
    public abstract IncapableCause filter(Context context, MediaItem item);

    /**
     * Whether an {@link Item} need filtering.
     */
    protected boolean needFiltering(Context context, MediaItem item) {
        for (MimeType type : constraintTypes()) {
            if (type.checkType(context.getContentResolver(), item.getContentUri())) {
                return true;
            }
        }
        return false;
    }
}
