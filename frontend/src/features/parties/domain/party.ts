export type Party = {
  id: number
  name: string
  email: string | null
}

export type PartyDraft = {
  name: string
  email: string
}

export const emptyPartyDraft: PartyDraft = {
  name: '',
  email: '',
}
