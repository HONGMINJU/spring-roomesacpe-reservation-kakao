package nextstep.controller;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import nextstep.dto.ThemeRequestDto;
import nextstep.dto.ThemeResponseDto;
import nextstep.service.ReservationService;
import nextstep.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<URI> createTheme(@RequestBody @Valid ThemeRequestDto themeRequestDto) throws SQLException {
        Long id = themeService.create(themeRequestDto);
        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTheme(@RequestBody @Valid ThemeRequestDto themeRequestDto, @PathVariable("id") Long themeId)
        throws SQLException {
        if (reservationService.isExistByThemeId(themeId)) {
            return ResponseEntity.badRequest().build();
        }
        themeService.update(themeRequestDto, themeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    private ResponseEntity<List<ThemeResponseDto>> getThemeList() throws SQLException {
        return ResponseEntity.ok().body(themeService.getThemeList());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTheme(@PathVariable("id") Long themeId) throws SQLException {
        themeService.delete(themeId);
        return ResponseEntity.noContent().build();
    }

}
