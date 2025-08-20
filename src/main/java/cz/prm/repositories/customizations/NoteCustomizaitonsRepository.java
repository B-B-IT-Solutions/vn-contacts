package cz.prm.repositories.customizations;

import cz.prm.domain.customizations.note.NoteCustomizations;
import cz.prm.repositories.extensions.executors.PrmQuerydslPredicateExecutor;
import cz.prm.repositories.extensions.repositories.RefreshAwareRepository;

public interface NoteCustomizaitonsRepository extends RefreshAwareRepository<NoteCustomizations, Long>,
    PrmQuerydslPredicateExecutor<NoteCustomizations> {

}
