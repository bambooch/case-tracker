import type { Note } from '../../notes/domain/note'
import type { Participant } from '../../participants/domain/participant'

export type CaseDetail = {
  id: number
  title: string
  status: string
  attentionLevel: string
  notes: Note[]
  participants: Participant[]
}
