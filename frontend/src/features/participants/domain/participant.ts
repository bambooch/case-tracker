export const participantRoles = ['CLAIMANT', 'RESPONDENT', 'COUNSEL', 'WITNESS'] as const
export type ParticipantRole = (typeof participantRoles)[number]

export const participantRoleLabels: Record<ParticipantRole, string> = {
  CLAIMANT: 'Tužilac',
  RESPONDENT: 'Tuženi',
  COUNSEL: 'Advokat',
  WITNESS: 'Svjedok',
}

export const roleBadgeClasses: Record<string, string> = {
  CLAIMANT: 'bg-violet-100 text-violet-800 ring-1 ring-inset ring-violet-200',
  RESPONDENT: 'bg-orange-100 text-orange-800 ring-1 ring-inset ring-orange-200',
  COUNSEL: 'bg-sky-100 text-sky-800 ring-1 ring-inset ring-sky-200',
  WITNESS: 'bg-teal-100 text-teal-800 ring-1 ring-inset ring-teal-200',
}

export type Participant = {
  partyId: number
  partyName: string
  role: string
}

export type ParticipantDraft = {
  partyId: number | ''
  role: ParticipantRole | ''
}

export const emptyParticipantDraft: ParticipantDraft = {
  partyId: '',
  role: '',
}
