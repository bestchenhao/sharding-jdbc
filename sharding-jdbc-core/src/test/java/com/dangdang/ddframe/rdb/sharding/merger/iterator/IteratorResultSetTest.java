/**
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package com.dangdang.ddframe.rdb.sharding.merger.iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;

import com.dangdang.ddframe.rdb.sharding.merger.ResultSetFactory;
import com.dangdang.ddframe.rdb.sharding.merger.fixture.MockResultSet;
import com.dangdang.ddframe.rdb.sharding.parser.result.merger.Limit;
import com.dangdang.ddframe.rdb.sharding.parser.result.merger.MergeContext;

public final class IteratorResultSetTest {
    
    @Test
    public void assertNext() throws SQLException {
        ResultSet resultSet = ResultSetFactory.getResultSet(Arrays.<ResultSet>asList(
                new MockResultSet<Integer>(1), new MockResultSet<Integer>(2, 4), new MockResultSet<Integer>()), new MergeContext());
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        assertThat(count, is(3));
    }
    
    @Test
    public void assertNextWithLimitForAllData() throws SQLException {
        MergeContext mergeContext = new MergeContext();
        mergeContext.setLimit(new Limit(1, 10));
        ResultSet resultSet = ResultSetFactory.getResultSet(Arrays.<ResultSet>asList(
                new MockResultSet<Integer>(1), new MockResultSet<Integer>(2, 4), new MockResultSet<Integer>()), mergeContext);
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        assertThat(count, is(2));
    }
    
    @Test
    public void assertNextWithLimitForPartData() throws SQLException {
        MergeContext mergeContext = new MergeContext();
        mergeContext.setLimit(new Limit(1, 1));
        ResultSet resultSet = ResultSetFactory.getResultSet(Arrays.<ResultSet>asList(
                new MockResultSet<Integer>(1), new MockResultSet<Integer>(2, 4), new MockResultSet<Integer>()), mergeContext);
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        assertThat(count, is(1));
    }
}
