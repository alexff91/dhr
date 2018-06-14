package com.dhr.services;

import com.dhr.model.statistics.Statistic;

public interface StatisticService {
    Statistic save(Statistic company);

    Iterable<Statistic> getAll();
}
