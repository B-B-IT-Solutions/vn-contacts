package cz.prm.domain.common;

import static cz.prm.utils.TestUtils.uuid;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.domain.Sort.by;
import static org.springframework.data.domain.Sort.unsorted;

import cz.prm.domain.common.query.Query;
import org.junit.jupiter.api.Test;

class QueryTest {

    @Test
    void newInstance() {
        var query = new Query();
        assertThat(query.getSort()).isNull();
        assertThat(query.getPagination()).isNotNull();
    }

    @Test
    void resolveSortNoSort() {
        var q1 = new Query(null, null);
        assertThat(q1.resolveSort()).isEqualTo(unsorted());

        var q2 = new Query("", null);
        assertThat(q2.resolveSort()).isEqualTo(unsorted());

        var value = uuid();
        var q3 = new Query(value, null);
        assertThat(q3.resolveSort()).isEqualTo(by(value));
    }

    @Test
    void resolveSortUnknownDirectionSort() {
        var direction = uuid().replace("-", "");
        var value = uuid();
        var sort = format("%s(%s)", direction, value);
        var q = new Query(sort, null);
        assertThat(q.resolveSort()).isEqualTo(by(sort));
    }

    @Test
    void resolveSortAscSort() {
        var value = uuid();
        var sort = format("asc(%s)", value);
        var q = new Query(sort, null);
        assertThat(q.resolveSort()).isEqualTo(by(ASC, value));
    }

    @Test
    void resolveSortDescSort() {
        var value = uuid();
        var sort = format("desc(%s)", value);
        var q = new Query(sort, null);
        assertThat(q.resolveSort()).isEqualTo(by(DESC, value));
    }

    @Test
    void resolveSortAscSortMultipleValues() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var values = format("%s,%s,%s", value1, value2, value3);
        var sort = format("asc(%s)", values);
        var q = new Query(sort, null);
        assertThat(q.resolveSort()).isEqualTo(by(ASC, value1, value2, value3));
    }

    @Test
    void resolveSortDescSortMultipleValues() {
        var value1 = uuid();
        var value2 = uuid();
        var value3 = uuid();
        var values = format("%s,%s,%s", value1, value2, value3);
        var sort = format("desc(%s)", values);
        var q = new Query(sort, null);
        assertThat(q.resolveSort()).isEqualTo(by(DESC, value1, value2, value3));
    }
}