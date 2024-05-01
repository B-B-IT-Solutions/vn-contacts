package cz.prm.repositories.common.query;

import static org.assertj.core.api.Assertions.assertThat;

import com.querydsl.core.BooleanBuilder;
import cz.prm.domain.contact.querydsl.QContact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PredicateCriteriaUtilsTest {

   private BooleanBuilder mockPredicate;

   @BeforeEach
   void setUp() {
      mockPredicate = new BooleanBuilder();
   }

   @Test
   void applyCriteriaNoOperation() {
      var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "firstName_001");
      var queryPattern = "containsIc(contact.firstName,firstName_001)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "lastName_001");
      queryPattern = "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.email, "email_001");
      queryPattern =
          "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001) && containsIc(contact.email," + "email_001)";
      assertThat(predicate).hasToString(queryPattern);
   }

   @Test
   void applyCriteriaContainsOperation() {
      var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "contains(firstName_001)");
      var queryPattern = "containsIc(contact.firstName,firstName_001)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "contains(lastName_001)");
      queryPattern = "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.email, "contains(email_001)");
      queryPattern =
          "containsIc(contact.firstName,firstName_001) && containsIc(contact.lastName,lastName_001) && containsIc(contact.email," + "email_001)";
      assertThat(predicate).hasToString(queryPattern);
   }

   @Test
   void applyCriteriaNotContainsOperation() {
      var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName, "notContains(firstName_001)");
      var queryPattern = "!containsIc(contact.firstName,firstName_001)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "notContains(lastName_001)");
      queryPattern = "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.lastName,lastName_001)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.email, "notContains(email_001)");
      queryPattern =
          "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.lastName,lastName_001) && !containsIc(contact.email," + "email_001)";
      assertThat(predicate).hasToString(queryPattern);
   }

   @Test
   void containsOperationWithMultipleFilterValues() {
      var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
          "contains(firstName_001,firstName_002,firstName_003)");
      var queryPattern = "containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact"
          + ".firstName,firstName_003)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "contains(lastName_001,lastName_002,lastName_003)");
      queryPattern = "(containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact.firstName,"
          + "firstName_003)) && (containsIc(contact.lastName,lastName_001) || containsIc(contact.lastName,lastName_002) || containsIc(contact"
          + ".lastName,lastName_003))";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.email, "containsIc(email_001,email_002,email_003)");
      queryPattern = "(containsIc(contact.firstName,firstName_001) || containsIc(contact.firstName,firstName_002) || containsIc(contact.firstName,"
          + "firstName_003)) && (containsIc(contact.lastName,lastName_001) || containsIc(contact.lastName,lastName_002) || containsIc(contact"
          + ".lastName,lastName_003)) && (containsIc(contact.email,email_001) || containsIc(contact.email,email_002) || containsIc(contact.email,"
          + "email_003))";
      assertThat(predicate).hasToString(queryPattern);
   }

   @Test
   void notContainsOperationWithMultipleFilterValues() {
      var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
          "notContains(firstName_001,firstName_002,firstName_003)");
      var queryPattern = "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && !containsIc(contact"
          + ".firstName,firstName_003)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName,
          "notContains(lastName_001,lastName_002,lastName_003)");
      queryPattern = "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && !containsIc(contact.firstName,"
          + "firstName_003) && !containsIc(contact.lastName,lastName_001) && !containsIc(contact.lastName,lastName_002) && !containsIc(contact"
          + ".lastName,lastName_003)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.email, "notContains(email_001,email_002,email_003)");
      queryPattern = "!containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && !containsIc(contact.firstName,"
          + "firstName_003) && !containsIc(contact.lastName,lastName_001) && !containsIc(contact.lastName,lastName_002) && !containsIc(contact"
          + ".lastName,lastName_003) && !containsIc(contact.email,email_001) && !containsIc(contact.email,email_002) && !containsIc(contact.email,"
          + "email_003)";
      assertThat(predicate).hasToString(queryPattern);
   }

   @Test
   void containsAndNotContainsOperationFilter() {
      var predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.firstName,
          "contains(firstName_001)+notContains(firstName_002)");
      var queryPattern = "containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.lastName, "contains(lastName_001)+notContains(lastName_002)");
      queryPattern = "containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && containsIc(contact.lastName,"
          + "lastName_001) && !containsIc(contact.lastName,lastName_002)";
      assertThat(predicate).hasToString(queryPattern);

      predicate = PredicateCriteriaUtils.applyCriteria(mockPredicate, QContact.contact.email, "contains(email_001)+notContains(email_002)");
      queryPattern = "containsIc(contact.firstName,firstName_001) && !containsIc(contact.firstName,firstName_002) && containsIc(contact.lastName,"
          + "lastName_001) && !containsIc(contact.lastName,lastName_002) && containsIc(contact.email,email_001) && !containsIc(contact"
          + ".email,email_002)";
      assertThat(predicate).hasToString(queryPattern);
   }
}