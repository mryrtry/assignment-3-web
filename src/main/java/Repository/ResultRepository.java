package Repository;

import Entity.Result;

import java.util.List;

public interface ResultRepository {

    Result saveResult(Result result);

    List<Result> getAllResults();

    List<Result> removeAllResults();

}