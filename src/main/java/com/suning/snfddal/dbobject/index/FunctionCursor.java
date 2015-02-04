/*
 * Copyright 2004-2014 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group
 */
package com.suning.snfddal.dbobject.index;

import com.suning.snfddal.message.DbException;
import com.suning.snfddal.result.ResultInterface;
import com.suning.snfddal.result.Row;
import com.suning.snfddal.result.SearchRow;
import com.suning.snfddal.value.Value;

/**
 * A cursor for a function that returns a result.
 */
public class FunctionCursor implements Cursor {

    private final ResultInterface result;
    private Value[] values;
    private Row row;

    FunctionCursor(ResultInterface result) {
        this.result = result;
    }

    @Override
    public Row get() {
        if (values == null) {
            return null;
        }
        if (row == null) {
            row = new Row(values, 1);
        }
        return row;
    }

    @Override
    public SearchRow getSearchRow() {
        return get();
    }

    @Override
    public boolean next() {
        row = null;
        if (result != null && result.next()) {
            values = result.currentRow();
        } else {
            values = null;
        }
        return values != null;
    }

    @Override
    public boolean previous() {
        throw DbException.throwInternalError();
    }

}