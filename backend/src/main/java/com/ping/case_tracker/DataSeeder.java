package com.ping.case_tracker;

import com.ping.case_tracker.casework.application.CaseService;
import com.ping.case_tracker.casework.application.NoteService;
import com.ping.case_tracker.casework.application.PartyService;
import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;
import com.ping.case_tracker.casework.domain.model.enums.PartyRole;
import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.model.records.Party;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class DataSeeder implements ApplicationRunner {

    private final CaseService caseService;
    private final NoteService noteService;
    private final PartyService partyService;

    public DataSeeder(CaseService caseService, NoteService noteService, PartyService partyService) {
        this.caseService = caseService;
        this.noteService = noteService;
        this.partyService = partyService;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!caseService.findCases(null, null).isEmpty()) {
            return;
        }

        Party kovacevic  = partyService.createParty("Amir Kovačević",       "amir.kovacevic@gmail.com");
        Party hadzic     = partyService.createParty("Lejla Hadžić",          "lejla.hadzic@example.ba");
        Party begovic    = partyService.createParty("Mirza Begović",          "mirza.begovic@example.ba");
        Party mehmedovic = partyService.createParty("Selma Mehmedović",       "selma.mehmedovic@example.ba");
        Party dzankovic  = partyService.createParty("Adv. Dino Džanković",   "advokat.dzankovic@pravnafirma.ba");
        Party karic      = partyService.createParty("Adv. Nermina Karić",    "n.karic@karicipartneri.ba");
        Party firma      = partyService.createParty("Balkan Trade d.o.o.",    "info@balkantrade.ba");
        Party causevic   = partyService.createParty("Emir Čaušević",          "emir.causevic@example.ba");

        // 1
        Case c1 = caseService.createCase("Spor oko vlasništva nekretnine u Sarajevu", CaseStatus.OPEN);
        noteService.addNote(c1.id(), "Podnesena tužba Općinskom sudu u Sarajevu. Čeka se zakazivanje ročišta.", "Adv. Džanković");
        noteService.addNote(c1.id(), "Tuženi dostavio dokaze o kupoprodajnom ugovoru iz 2018. godine.", "Adv. Džanković");
        partyService.addParticipant(c1.id(), kovacevic.id(),  PartyRole.CLAIMANT);
        partyService.addParticipant(c1.id(), begovic.id(),    PartyRole.RESPONDENT);
        partyService.addParticipant(c1.id(), dzankovic.id(), PartyRole.COUNSEL);

        // 2
        Case c2 = caseService.createCase("Razvod braka – Porodica Hodžić", CaseStatus.IN_REVIEW);
        noteService.addNote(c2.id(), "Ročište zakazano za 22.05.2026. Stranke nisu postigle dogovor o starateljstvu.", "Adv. Karić");
        noteService.addNote(c2.id(), "Sud naložio vještačenje imovine i procjenu vrijednosti porodičnih nekretnina.", "Adv. Karić");
        partyService.addParticipant(c2.id(), hadzic.id(), PartyRole.CLAIMANT);
        partyService.addParticipant(c2.id(), karic.id(),  PartyRole.COUNSEL);

        // 3
        Case c3 = caseService.createCase("Tužba za naknadu štete – Saobraćajna nesreća na M-17", CaseStatus.OPEN);
        noteService.addNote(c3.id(), "Tužilac zahtijeva naknadu u iznosu 45.000 KM zbog trajnih tjelesnih oštećenja.", null);
        partyService.addParticipant(c3.id(), causevic.id(),  PartyRole.CLAIMANT);
        partyService.addParticipant(c3.id(), kovacevic.id(), PartyRole.RESPONDENT);
        partyService.addParticipant(c3.id(), dzankovic.id(), PartyRole.COUNSEL);

        // 4
        Case c4 = caseService.createCase("Ugovorni spor – Balkan Trade d.o.o.", CaseStatus.IN_REVIEW);
        noteService.addNote(c4.id(), "Firma nije isporučila robu u ugovorenom roku. Tužilac traži raskid ugovora i povrat avansa.", "Adv. Karić");
        noteService.addNote(c4.id(), "Pribavljeni bankarski izvodi koji potvrđuju uplatu avansa od 30.000 KM.", "Adv. Karić");
        partyService.addParticipant(c4.id(), mehmedovic.id(), PartyRole.CLAIMANT);
        partyService.addParticipant(c4.id(), firma.id(),      PartyRole.RESPONDENT);
        partyService.addParticipant(c4.id(), karic.id(),      PartyRole.COUNSEL);

        // 5
        Case c5 = caseService.createCase("Nasljednički spor – Ostavina Mujović", CaseStatus.OPEN);
        noteService.addNote(c5.id(), "Tri nasljednika osporavaju testament. Zapažena nepravilnost u notarskoj ovjeri.", null);
        partyService.addParticipant(c5.id(), mehmedovic.id(), PartyRole.CLAIMANT);
        partyService.addParticipant(c5.id(), dzankovic.id(),  PartyRole.COUNSEL);

        // 6
        Case c6 = caseService.createCase("Radni spor – Nezakonito otpuštanje", CaseStatus.CLOSED);
        noteService.addNote(c6.id(), "Sud presudio u korist tužioca. Tuženi dužan isplatiti šestomjesečnu platu kao odštetu.", "Adv. Džanković");
        noteService.addNote(c6.id(), "Predmet zaključen, odšteta isplaćena 10.04.2026.", "Adv. Džanković");
        partyService.addParticipant(c6.id(), begovic.id(),   PartyRole.CLAIMANT);
        partyService.addParticipant(c6.id(), dzankovic.id(), PartyRole.COUNSEL);

        // 7
        Case c7 = caseService.createCase("Krivična prijava – Prevara putem interneta", CaseStatus.OPEN);
        noteService.addNote(c7.id(), "Prijava podnijeta Tužilaštvu KS. Oštećeni izgubio 8.500 KM u lažnoj online investiciji.", null);
        partyService.addParticipant(c7.id(), causevic.id(), PartyRole.CLAIMANT);
        partyService.addParticipant(c7.id(), karic.id(),    PartyRole.COUNSEL);

        // 8
        Case c8 = caseService.createCase("Neispunjenje kupoprodajnog ugovora – Stan u Ilidži", CaseStatus.IN_REVIEW);
        noteService.addNote(c8.id(), "Prodavac odbio predati ključeve stana unatoč potpunoj uplati kupoprodajne cijene.", "Adv. Karić");
        partyService.addParticipant(c8.id(), hadzic.id(),    PartyRole.CLAIMANT);
        partyService.addParticipant(c8.id(), kovacevic.id(), PartyRole.RESPONDENT);
        partyService.addParticipant(c8.id(), karic.id(),     PartyRole.COUNSEL);

        // 9
        Case c9 = caseService.createCase("Pritužba – Diskriminacija na radnom mjestu", CaseStatus.CLOSED);
        noteService.addNote(c9.id(), "Komisija za ravnopravnost utvrdila diskriminaciju. Poslodavac sankcionisan administrativnom kaznom.", null);
        noteService.addNote(c9.id(), "Predmet zatvoren po pravosnažnosti rješenja, 28.02.2026.", null);
        partyService.addParticipant(c9.id(), mehmedovic.id(), PartyRole.CLAIMANT);

        // 10
        Case c10 = caseService.createCase("Administrativna žalba – Rješenje Općine Centar", CaseStatus.OPEN);
        noteService.addNote(c10.id(), "Žalba izjavljena na rješenje o odbijanju zahtjeva za građevinsku dozvolu.", "Adv. Džanković");
        noteService.addNote(c10.id(), "Pribavljeno mišljenje urbanista koje potvrđuje usklađenost projekta s prostornim planom.", "Adv. Džanković");
        partyService.addParticipant(c10.id(), kovacevic.id(),  PartyRole.CLAIMANT);
        partyService.addParticipant(c10.id(), dzankovic.id(), PartyRole.COUNSEL);
    }
}
