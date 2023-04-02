//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 03/23
// * Definition: Implementation of CustomLocationRepository class.
//**********************************************************************************************************************

package com.zilinli.staybooking.repository;
//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.zilinli.staybooking.model.Location;

// Framework includes
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

// System includes
import java.util.ArrayList;
import java.util.List;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************
public class CustomLocationRepositoryImpl implements CustomLocationRepository {

//**********************************************************************************************************************
// * Class constructors
//**********************************************************************************************************************
    public CustomLocationRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticSearchOperations = elasticsearchOperations;
    }
//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    @Override
    public List<Long> searchByDistance(double lat, double lon, String dist) {
        if (dist == null || dist.isEmpty()) {
            dist = DEFAULT_DISTANCE;
        }

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withFilter(new GeoDistanceQueryBuilder("geoPoint").point(lat, lon).distance(dist, DistanceUnit.KILOMETERS));
        SearchHits<Location> searchResult = elasticSearchOperations.search(queryBuilder.build(), Location.class);
        List<Long> locationIDs = new ArrayList<>();
        for (SearchHit<Location> hit : searchResult.getSearchHits()) {
            locationIDs.add(hit.getContent().getId());
        }
        return locationIDs;

    }
//**********************************************************************************************************************
// * Protected methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private methods
//**********************************************************************************************************************

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************

    private static final String DEFAULT_DISTANCE = "50";
    private final ElasticsearchOperations elasticSearchOperations;

}
