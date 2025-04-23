package citytech.global.usecases.excel;

import citytech.global.platform.rest.RestResponse;
import citytech.global.platform.usecase.UseCase;
import citytech.global.repository.user.UserEntity;
import citytech.global.repository.user.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Singleton
public class DbToExcelUseCase implements UseCase<DbToExcelUseCaseRequest, DbToExcelUseCaseResponse> {
    private final UserRepository userRepository;

    @Inject
    public DbToExcelUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    Logger logger = Logger.getLogger(DbToExcelUseCase.class.getName());
    @Override
    public Optional<DbToExcelUseCaseResponse> exceute(DbToExcelUseCaseRequest request) throws IOException {
        List<UserEntity> users = fetchUsers();
        writeToExcel(users);
        return Optional.of(new DbToExcelUseCaseResponse(RestResponse.success().message()));
    }
    private void writeToExcel(List<UserEntity> users) throws IOException {
        Dotenv env = Dotenv.load();
        String filepath = env.get("DESKTOP_FILEPATH");
        String columnHead = env.get("COLUMN_HEAD");
        FileWriter writer = new FileWriter(filepath);
            writer.write(columnHead);
            for (UserEntity user : users) {
                writer.write(
                        "\"" + user.getId() + "\"," +
                                "\"" + user.getName() + "\"," +
                                "\"" + user.getEmailId() + "\"," +
                                "\"" + user.getPassword() + "\"," +
                                "\"" + user.getAddress() + "\"," +
                                "\"" + user.getCitizenShip() + "\"," +
                                "\"" + user.getAvailableBalance() + "\"," +
                                "\"" + user.getRoles() + "\"," +
                                "\"" + user.getStatus() + "\"\n"
                );
            }
    }
    private List<UserEntity> fetchUsers() {
        return userRepository.findAll();
    }
}
