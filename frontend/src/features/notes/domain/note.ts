export type Note = {
  id: number
  caseId: number
  content: string
  author: string | null
  createdAt: string
}

export type NoteDraft = {
  content: string
  author: string
}

export const emptyNoteDraft: NoteDraft = {
  content: '',
  author: '',
}
