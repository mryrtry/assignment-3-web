package Repository;

import Entity.Result;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Alternative
public class JdbcRepository implements ResultRepository {

    private final String url = "jdbc:postgresql://localhost:5432/studs";
    private final String user = "s409483";
    private final String password = "vh15UEERqzzV8upU";

    @Override
    public Result saveResult(Result result) {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO results (x, y, r, requesttime, inarea, processingtime) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setFloat(1, result.getX());
            statement.setFloat(2, result.getY());
            statement.setFloat(3, result.getR());
            statement.setString(4, result.getRequestTime());
            statement.setBoolean(5, result.isInArea());
            statement.setLong(6, result.getProcessingTime());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                result.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            return null;
        }
        return result;
    }

    @Override
    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM results";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Result result = new Result();
                result.setId(resultSet.getLong("id"));
                result.setX(resultSet.getFloat("x"));
                result.setY(resultSet.getFloat("y"));
                result.setR(resultSet.getFloat("r"));
                result.setRequestTime(resultSet.getString("requesttime"));
                result.setInArea(resultSet.getBoolean("inarea"));
                result.setProcessingTime(resultSet.getLong("processingtime"));
                results.add(result);
            }

            return results;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Result> removeAllResults() {
        List<Result> results = getAllResults();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM results";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            return results;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}