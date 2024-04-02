package ua.com.testsupport.datagenerator.contentdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing simple dto for test cases.
 *
 * @author Zakhar Kuropiatnyk
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestDto {

  private String username;

  private String password;
}
